import com.example.pokerscore.Player
import com.example.pokerscore.PokerScore
import org.junit.Assert.assertEquals
import org.junit.Test

class PokerGainsCalculatorTest {

    @Test
    fun `test calculatePokerGains avec une paire gagnante`() {
        val players = listOf(
            Player("Joueur 1", 100, listOf("Ah", "Ad")),
            Player("Joueur 2", 150, listOf("Ks", "Kd")),
            Player("Joueur 3", 200, listOf("Qc", "Qh"))
        )
        val potAmount = 300
        val communityCards = listOf("2s", "3d", "Th", "Qd", "Kc")

        val expectedGains = mapOf(
            "Joueur 1" to 0,
            "Joueur 2" to 150,
            "Joueur 3" to 150
        )

        val score = PokerScore()
        val actualGains = score.calculatePokerGains(players, potAmount, communityCards)

        assertEquals(expectedGains, actualGains)
    }

    @Test
    fun `test calculatePokerGains avec une suite gagnante`() {
        val players = listOf(
            Player("Joueur 1", 100, listOf("Ah", "6d")),
            Player("Joueur 2", 150, listOf("Ks", "Kd")),
            Player("Joueur 3", 200, listOf("Qc", "Qh"))
        )
        val potAmount = 300
        val communityCards = listOf("2s", "3d", "4h", "5d", "Kc")

        val expectedGains = mapOf(
            "Joueur 1" to 200,
            "Joueur 2" to 50,
            "Joueur 3" to 50
        )

        val score = PokerScore()
        val actualGains = score.calculatePokerGains(players, potAmount, communityCards)

        assertEquals(expectedGains, actualGains)
    }

    //à réparé
    @Test
    fun `test evaluateHand avec une quinte flush royal`() {
        val cards = listOf("Ad", "Kd","Qd", "Td", "10d", "5d", "Kc")
        val score = PokerScore()

        val resultHand = score.evaluateHand(cards)
        val expectedHand = PokerScore.Hand.RoyalFlush

        assertEquals(expectedHand, resultHand)
    }

    //à réparé
    @Test
    fun `test evaluateHand avec une quinte flush`() {
        val cards = listOf("Ah", "6h","2h", "3h", "4h", "5h", "Kc")
        val score = PokerScore()

        val resultHand = score.evaluateHand(cards)
        val expectedHand = PokerScore.Hand.StraightFlush

        assertEquals(expectedHand, resultHand)
    }

    @Test
    fun `test evaluateHand avec une suite`() {
        val cards = listOf("Ah", "6d","2s", "3d", "4h", "5d", "Kc")
        val score = PokerScore()

        val resultHand = score.evaluateHand(cards)
        val expectedHand = PokerScore.Hand.Straight

        assertEquals(expectedHand, resultHand)
    }

    @Test
    fun `test evaluateHand avec une Fullhouse`() {
        val cards = listOf("Ad", "As","Ah", "3d", "3h", "5d", "Kc")
        val score = PokerScore()

        val resultHand = score.evaluateHand(cards)
        val expectedHand = PokerScore.Hand.FullHouse

        assertEquals(expectedHand, resultHand)
    }

    @Test
    fun `test evaluateHand avec une couleur`() {
        val cards = listOf("Ad", "Kd","Td", "3d", "4h", "5d", "Kc")
        val score = PokerScore()

        val resultHand = score.evaluateHand(cards)
        val expectedHand = PokerScore.Hand.Flush

        assertEquals(expectedHand, resultHand)
    }

    @Test
    fun `test evaluateHand avec un carré`() {
        val cards = listOf("Ac", "Kd","Ts", "3d", "Kh", "Ks", "Kc")
        val score = PokerScore()

        val resultHand = score.evaluateHand(cards)
        val expectedHand = PokerScore.Hand.FourOfAKind

        assertEquals(expectedHand, resultHand)
    }

    @Test
    fun `test evaluateHand avec un brelan`() {
        val cards = listOf("5c", "Kd","Ts", "3d", "5h", "5s", "Kc")
        val score = PokerScore()

        val resultHand = score.evaluateHand(cards)
        val expectedHand = PokerScore.Hand.ThreeOfAKind

        assertEquals(expectedHand, resultHand)
    }

    @Test
    fun `test evaluateHand avec une double paire`() {
        val cards = listOf("Ac", "Kd","Ts", "3d", "4h", "As", "Kc")
        val score = PokerScore()

        val resultHand = score.evaluateHand(cards)
        val expectedHand = PokerScore.Hand.TwoPair

        assertEquals(expectedHand, resultHand)
    }

    @Test
    fun `test evaluateHand avec une paire`() {
        val cards = listOf("Ac", "Kd","Ts", "3d", "4h", "9s", "Kc")
        val score = PokerScore()

        val resultHand = score.evaluateHand(cards)
        val expectedHand = PokerScore.Hand.Pair

        assertEquals(expectedHand, resultHand)
    }
}
