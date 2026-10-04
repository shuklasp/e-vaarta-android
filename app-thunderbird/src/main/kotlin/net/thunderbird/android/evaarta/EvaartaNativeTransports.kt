package net.thunderbird.android.evaarta

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore

data class EvaartaDiscoveredPeer(
    val actorId: String,
    val fingerprint: String,
    val host: String,
    val port: Int
)

object EvaartaAndroidKeyStore {
    private const val PROVIDER = "AndroidKeyStore"

    fun getOrCreateEcKeyPair(alias: String): KeyPair {
        val store = KeyStore.getInstance(PROVIDER).apply { load(null) }
        if (store.containsAlias(alias)) {
            val entry = store.getEntry(alias, null) as KeyStore.PrivateKeyEntry
            return KeyPair(entry.certificate.publicKey, entry.privateKey)
        }
        val generator = KeyPairGenerator.getInstance("EC", PROVIDER)
        generator.initialize(256)
        return generator.generateKeyPair()
    }
}

class EvaartaWifiSocketTransport {
    suspend fun send(peer: EvaartaDiscoveredPeer, payload: ByteArray): Result<Unit> = runCatching {
        Socket().use { socket ->
            socket.connect(InetSocketAddress(peer.host, peer.port), 5_000)
            DataOutputStream(socket.getOutputStream()).use { output ->
                output.writeInt(payload.size)
                output.write(payload)
                output.flush()
            }
        }
    }

    fun listen(port: Int, onPayload: (ByteArray) -> Unit): ServerSocket {
        val server = ServerSocket(port)
        Thread {
            while (!server.isClosed) {
                val socket = server.accept()
                Thread {
                    socket.use {
                        val input = DataInputStream(it.getInputStream())
                        val size = input.readInt()
                        require(size in 0..16 * 1024 * 1024) { "invalid e-Vaarta frame size" }
                        val payload = ByteArray(size)
                        input.readFully(payload)
                        onPayload(payload)
                    }
                }.start()
            }
        }.start()
        return server
    }
}

class EvaartaNsdDiscovery(
    context: Context,
    private val serviceType: String = "_evaarta._tcp."
) {
    private val nsd = context.getSystemService(Context.NSD_SERVICE) as NsdManager

    fun discover(onPeer: (EvaartaDiscoveredPeer) -> Unit): NsdManager.DiscoveryListener {
        val listener = object : NsdManager.DiscoveryListener {
            override fun onDiscoveryStarted(serviceType: String) = Unit
            override fun onDiscoveryStopped(serviceType: String) = Unit
            override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) = Unit
            override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) = Unit
            override fun onServiceLost(serviceInfo: NsdServiceInfo) = Unit
            override fun onServiceFound(serviceInfo: NsdServiceInfo) {
                nsd.resolveService(serviceInfo, object : NsdManager.ResolveListener {
                    override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) = Unit
                    override fun onServiceResolved(info: NsdServiceInfo) {
                        val attributes = info.attributes
                        val actorId = attributes["actorId"]?.toString(Charsets.UTF_8) ?: return
                        val fingerprint = attributes["fingerprint"]?.toString(Charsets.UTF_8) ?: return
                        onPeer(EvaartaDiscoveredPeer(actorId, fingerprint, info.host.hostAddress ?: return, info.port))
                    }
                })
            }
        }
        nsd.discoverServices(serviceType, NsdManager.PROTOCOL_DNS_SD, listener)
        return listener
    }

    fun stop(listener: NsdManager.DiscoveryListener) = nsd.stopServiceDiscovery(listener)
}
