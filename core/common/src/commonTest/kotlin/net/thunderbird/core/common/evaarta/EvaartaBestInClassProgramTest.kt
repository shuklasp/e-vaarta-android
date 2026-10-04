package net.thunderbird.core.common.evaarta
import kotlin.test.Test
import kotlin.test.assertTrue
class EvaartaBestInClassProgramTest{@Test fun coverage(){assertTrue(EvaartaBestInClassProgram.classes.size>=20);assertTrue(EvaartaBestInClassProgram.principles.contains("one-semantic-graph"))}@Test fun gates(){assertTrue(EvaartaBestInClassProgram.requiresHumanAndAdversarial(2,true,true))}}