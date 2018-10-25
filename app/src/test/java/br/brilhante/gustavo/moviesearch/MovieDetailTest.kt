package br.brilhante.gustavo.moviesearch

import br.brilhante.gustavo.moviesearch.utils.StringUtils
import org.junit.Assert.*
import org.junit.Test

class MovieDetailTest {

    @Test
    fun wordToPhraseEmptyText() {
        var list: List<String> = emptyList()
        assertEquals("", StringUtils.getWordsToPhrase("and", list))
    }

    @Test
    fun processGenreTextOneWord() {
        var list: List<String> = emptyList()
        list += "word"

        val phrase = StringUtils.getWordsToPhrase("and", list)

        assertNotEquals("", phrase)
        assertFalse(phrase.contains(Regex(" and ")))
        assertFalse(phrase.contains(Regex(", ")))
        assertFalse(phrase.contains(Regex("[^ ], ")))
    }

    @Test
    fun processGenreTextTwoWords() {
        var list: List<String> = emptyList()
        list += "word"
        list += "word2"

        val phrase = StringUtils.getWordsToPhrase("and", list)

        assertNotEquals("", phrase)
        assertTrue(phrase.contains(Regex(" and ")))
        assertFalse(phrase.contains(Regex(", ")))
        assertFalse(phrase.contains(Regex("[^ ], ")))
    }

    @Test
    fun processGenreTextTreeWords() {
        var list: List<String> = emptyList()
        list += "word"
        list += "word2"
        list += "word3"

        val phrase = StringUtils.getWordsToPhrase("and", list)

        assertNotEquals("", phrase)
        assertTrue(phrase.contains(Regex(" and ")))
        assertTrue(phrase.contains(Regex("[^ ], ")))
    }

}