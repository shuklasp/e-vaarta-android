package net.thunderbird.android.evaarta

import java.security.PrivateKey
import java.security.Signature
import java.util.Base64
import java.util.UUID

data class EvaartaSessionHello(
    val actorId: String,
    val fingerprint: String,
    val nonce: String = UUID.randomUUID().toString(),
    val signature: String
)

class EvaartaAndroidSessionSigner(privateKey: PrivateKey) {
    private val key = privateKey
    fun sign(value: String): String {
        val signature = Signature.getInstance("SHA256withECDSA")
        signature.initSign(key)
        signature.update(value.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(signature.sign())
    }
}

class EvaartaAuthenticatedSession(
    private val localActorId: String,
    private val localFingerprint: String,
    private val signer: EvaartaAndroidSessionSigner
) {
    private var localNonce: String? = null
    private var remoteNonce: String? = null
    var authenticated: Boolean = false
        private set

    fun createHello(): EvaartaSessionHello {
        val nonce = UUID.randomUUID().toString()
        localNonce = nonce
        val unsigned = "$localActorId|$localFingerprint|$nonce"
        return EvaartaSessionHello(localActorId, localFingerprint, nonce, signer.sign(unsigned))
    }

    fun acceptChallenge(challenge: String) {
        check(challenge == localNonce) { "e-Vaarta session challenge mismatch" }
        authenticated = true
    }

    fun rememberRemoteNonce(nonce: String) {
        remoteNonce = nonce
    }

    fun close() {
        localNonce = null
        remoteNonce = null
        authenticated = false
    }
}
