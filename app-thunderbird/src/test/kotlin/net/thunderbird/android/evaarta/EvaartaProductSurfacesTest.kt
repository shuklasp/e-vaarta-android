package net.thunderbird.android.evaarta

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
class EvaartaProductSurfacesTest { @Test fun defaultsAreOfflineFirst(){ val s=EvaartaWorkspaceSurfaceState(); assertTrue(s.offlineReady); assertEquals("",s.query); val sync=EvaartaSyncSurfaceState(); assertFalse(sync.networkEnabled); assertEquals("local-only",sync.status) } private fun assertTrue(v:Boolean)=kotlin.test.assertTrue(v) }
