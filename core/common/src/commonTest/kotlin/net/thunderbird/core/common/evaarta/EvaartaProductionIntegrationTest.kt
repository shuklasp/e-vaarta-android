package net.thunderbird.core.common.evaarta
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
class EvaartaProductionIntegrationTest{
 @Test fun authorizationFailsClosed(){val t=EvaartaCapabilityToken("u","send","conversation:c",nonce="n");assertTrue(EvaartaProductionIntegration.authorize(t,"send","conversation:c"));assertFalse(EvaartaProductionIntegration.authorize(t,"delete","conversation:c"))}
 @Test fun contractIsComplete(){assertTrue(EvaartaProductionIntegration.contract().contains("provider-adapters"));assertTrue(EvaartaProductionIntegration.contract().contains("release-validation"))}
}
