package net.thunderbird.android.evaarta
import kotlin.test.Test
import kotlin.test.assertEquals
class EvaartaRuntimeDistributedTest{@Test fun pairing(){val t=EvaartaTrustStore();val p=EvaartaPairingSession(t);p.begin(EvaartaTrustedPeer("b","pk","fp"));p.approve();assertEquals(true,t.isTrusted("b"))}}
