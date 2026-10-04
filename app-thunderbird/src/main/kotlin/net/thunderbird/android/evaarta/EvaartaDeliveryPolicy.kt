package net.thunderbird.android.evaarta
object EvaartaDeliveryPolicy { val localFirst=listOf("wifi-lan","bluetooth","file-bundle","matrix","xmpp","whatsapp","arattai","email"); fun shouldForward(e:EvaartaEnvelope)=e.hopLimit>0 }
