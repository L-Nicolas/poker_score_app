import com.example.pokerscore.board.BoardPoker
import com.example.pokerscore.board.CommunityCards
import com.example.pokerscore.board.PokerScore
import com.example.pokerscore.card.Card
import com.example.pokerscore.card.CardPoker
import com.example.pokerscore.card.Deck
import com.example.pokerscore.card.Figure
import com.example.pokerscore.card.Suit
import com.example.pokerscore.hand.Hand
import com.example.pokerscore.player.Player
import com.example.pokerscore.player.PlayerInGame
import org.junit.Assert.assertEquals
import org.junit.Test

class PokerGainsCalculatorTest {

   /* @Test
    fun `test calculatePokerGains avec un carré`() {
        val listCardsPlayer1 = listOf(
            CardPoker(Suit.CLUBS, Figure.QUEEN),
            CardPoker(Suit.HEARTS, Figure.QUEEN),
        )
        val listCardsPlayer2 = listOf(
            CardPoker(Suit.CLUBS, Figure.JACK),
            CardPoker(Suit.HEARTS, Figure.JACK),
        )
        val listCardsPlayer3 = listOf(
            CardPoker(Suit.CLUBS, Figure.ACE),
            CardPoker(Suit.HEARTS, Figure.ACE),
        )
        val players = listOf(
            PlayerInGame("Joueur 1", 100),
            PlayerInGame("Joueur 2", 100),
            PlayerInGame("Joueur 3", 100),
        )
        val potAmount = 300
        /*val communityCards = listOf(
            Card(Suit.CLUBS, Figure.FIVE),
            Card(Suit.HEARTS, Figure.FIVE),
            Card(Suit.DIAMONDS, Figure.FOUR),
            Card(Suit.SPADES, Figure.FOUR),
            Card(Suit.CLUBS, Figure.THREE),
        )*/

        val expectedGains = mapOf(
            "Joueur 1" to 0,
            "Joueur 2" to 0,
            "Joueur 3" to 300
        )

        val score = PokerScore()
        val deck = Deck()
        val communityCards = CommunityCards()
        communityCards.cards = listOf(
            CardPoker(Suit.CLUBS, Figure.FIVE),
            CardPoker(Suit.HEARTS, Figure.FIVE),
            CardPoker(Suit.DIAMONDS, Figure.FOUR),
            CardPoker(Suit.SPADES, Figure.FOUR),
            CardPoker(Suit.CLUBS, Figure.THREE),
        ).toMutableList()
        val board = BoardPoker(deck,communityCards)
        board.players = players as MutableList<PlayerInGame>
        players[0].setHand(Hand(listCardsPlayer1))
        players[1].setHand(Hand(listCardsPlayer2))
        players[2].setHand(Hand(listCardsPlayer3))
        val actualGains = score.calculatePokerGains(board)

        assertEquals(expectedGains, actualGains)
    }*/
/*
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
            "Joueur 2" to 100,
            "Joueur 3" to 0
        )

        val score = PokerScore()
        val actualGains = score.calculatePokerGains(players, potAmount, communityCards)

        assertEquals(expectedGains, actualGains)
    }

    //à réparé
    @Test
    fun `test evaluateHand avec une quinte flush royal`() {
        val cards = listOf("Ad", "Kd","Qd", "Jd", "10d", "5d", "4c")
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
        val cards = listOf("5c", "Qd","Ts", "3d", "5h", "5s", "Kc")
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

    @Test
    fun `test evaluateHand avec une carte haute`() {
        val cards = listOf("Ac", "Kd","Js", "3d", "4h", "9s", "Qc")
        val score = PokerScore()

        val resultHand = score.evaluateHand(cards)
        val expectedHand = PokerScore.Hand.None

        assertEquals(expectedHand, resultHand)
    }*/
}
