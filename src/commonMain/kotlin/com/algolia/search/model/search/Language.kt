package com.algolia.search.model.search

import com.algolia.search.model.Raw
import com.algolia.search.serialize.KeyAfrikaans
import com.algolia.search.serialize.KeyAlbanian
import com.algolia.search.serialize.KeyArabic
import com.algolia.search.serialize.KeyArmenian
import com.algolia.search.serialize.KeyAzeri
import com.algolia.search.serialize.KeyBasque
import com.algolia.search.serialize.KeyBrunei
import com.algolia.search.serialize.KeyBulgarian
import com.algolia.search.serialize.KeyCatalan
import com.algolia.search.serialize.KeyCzech
import com.algolia.search.serialize.KeyDanish
import com.algolia.search.serialize.KeyDutch
import com.algolia.search.serialize.KeyEnglish
import com.algolia.search.serialize.KeyEsperanto
import com.algolia.search.serialize.KeyEstonian
import com.algolia.search.serialize.KeyFaroese
import com.algolia.search.serialize.KeyFinnish
import com.algolia.search.serialize.KeyFrench
import com.algolia.search.serialize.KeyGalician
import com.algolia.search.serialize.KeyGeorgian
import com.algolia.search.serialize.KeyGerman
import com.algolia.search.serialize.KeyHebrew
import com.algolia.search.serialize.KeyHindi
import com.algolia.search.serialize.KeyHungarian
import com.algolia.search.serialize.KeyIcelandic
import com.algolia.search.serialize.KeyIndonesian
import com.algolia.search.serialize.KeyItalian
import com.algolia.search.serialize.KeyJapanese
import com.algolia.search.serialize.KeyKazakh
import com.algolia.search.serialize.KeyKorean
import com.algolia.search.serialize.KeyKyrgyz
import com.algolia.search.serialize.KeyLithuanian
import com.algolia.search.serialize.KeyMalay
import com.algolia.search.serialize.KeyMaltese
import com.algolia.search.serialize.KeyMaori
import com.algolia.search.serialize.KeyMarathi
import com.algolia.search.serialize.KeyMongolian
import com.algolia.search.serialize.KeyNorthernSotho
import com.algolia.search.serialize.KeyNorwegian
import com.algolia.search.serialize.KeyPashto
import com.algolia.search.serialize.KeyPolish
import com.algolia.search.serialize.KeyPortuguese
import com.algolia.search.serialize.KeyQuechua
import com.algolia.search.serialize.KeyRomanian
import com.algolia.search.serialize.KeyRussian
import com.algolia.search.serialize.KeySlovak
import com.algolia.search.serialize.KeySpanish
import com.algolia.search.serialize.KeySwahili
import com.algolia.search.serialize.KeySwedish
import com.algolia.search.serialize.KeyTagalog
import com.algolia.search.serialize.KeyTamil
import com.algolia.search.serialize.KeyTatar
import com.algolia.search.serialize.KeyTelugu
import com.algolia.search.serialize.KeyTswana
import com.algolia.search.serialize.KeyTurkish
import com.algolia.search.serialize.KeyWelsh
import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.internal.StringSerializer

@Deprecated(
    message = "Obsolete name from the preview version of library.",
    replaceWith = ReplaceWith("Language"),
    level = DeprecationLevel.WARNING
)
typealias QueryLanguage = Language

/**
 * List of supported languages with their associated language ISO code.
 */
@Serializable(Language.Companion::class)
sealed class Language(override val raw: String) : Raw<String> {

    object Afrikaans : Language(KeyAfrikaans)
    object Arabic : Language(KeyArabic)
    object Azeri : Language(KeyAzeri)
    object Bulgarian : Language(KeyBulgarian)
    object Brunei : Language(KeyBrunei)
    object Catalan : Language(KeyCatalan)
    object Czech : Language(KeyCzech)
    object Welsh : Language(KeyWelsh)
    object Danish : Language(KeyDanish)
    object German : Language(KeyGerman)
    object English : Language(KeyEnglish)
    object Esperanto : Language(KeyEsperanto)
    object Spanish : Language(KeySpanish)
    object Estonian : Language(KeyEstonian)
    object Basque : Language(KeyBasque)
    object Finnish : Language(KeyFinnish)
    object Faroese : Language(KeyFaroese)
    object French : Language(KeyFrench)
    object Galician : Language(KeyGalician)
    object Hebrew : Language(KeyHebrew)
    object Hindi : Language(KeyHindi)
    object Hungarian : Language(KeyHungarian)
    object Armenian : Language(KeyArmenian)
    object Indonesian : Language(KeyIndonesian)
    object Icelandic : Language(KeyIcelandic)
    object Italian : Language(KeyItalian)
    object Japanese : Language(KeyJapanese)
    object Georgian : Language(KeyGeorgian)
    object Kazakh : Language(KeyKazakh)
    object Korean : Language(KeyKorean)
    object Kyrgyz : Language(KeyKyrgyz)
    object Lithuanian : Language(KeyLithuanian)
    object Maori : Language(KeyMaori)
    object Mongolian : Language(KeyMongolian)
    object Marathi : Language(KeyMarathi)
    object Malay : Language(KeyMalay)
    object Maltese : Language(KeyMaltese)
    object Norwegian : Language(KeyNorwegian)
    object Dutch : Language(KeyDutch)
    object NorthernSotho : Language(KeyNorthernSotho)
    object Polish : Language(KeyPolish)
    object Pashto : Language(KeyPashto)
    object Portuguese : Language(KeyPortuguese)
    object Quechua : Language(KeyQuechua)
    object Romanian : Language(KeyRomanian)
    object Russian : Language(KeyRussian)
    object Slovak : Language(KeySlovak)
    object Albanian : Language(KeyAlbanian)
    object Swedish : Language(KeySwedish)
    object Swahili : Language(KeySwahili)
    object Tamil : Language(KeyTamil)
    object Telugu : Language(KeyTelugu)
    object Tagalog : Language(KeyTagalog)
    object Tswana : Language(KeyTswana)
    object Turkish : Language(KeyTurkish)
    object Tatar : Language(KeyTatar)

    data class Other(override val raw: String) : Language(raw)

    override fun toString(): String {
        return raw
    }

    companion object : KSerializer<Language> {

        private val serializer = StringSerializer

        override val descriptor = serializer.descriptor

        override fun serialize(encoder: Encoder, obj: Language) {
            serializer.serialize(encoder, obj.raw)
        }

        override fun deserialize(decoder: Decoder): Language {
            return when (val string = serializer.deserialize(decoder)) {
                KeyAfrikaans -> Afrikaans
                KeyArabic -> Arabic
                KeyAzeri -> Azeri
                KeyBulgarian -> Bulgarian
                KeyBrunei -> Brunei
                KeyCatalan -> Catalan
                KeyCzech -> Czech
                KeyWelsh -> Welsh
                KeyDanish -> Danish
                KeyGerman -> German
                KeyEnglish -> English
                KeyEsperanto -> Esperanto
                KeySpanish -> Spanish
                KeyEstonian -> Estonian
                KeyBasque -> Basque
                KeyFinnish -> Finnish
                KeyFaroese -> Faroese
                KeyFrench -> French
                KeyGalician -> Galician
                KeyHebrew -> Hebrew
                KeyHindi -> Hindi
                KeyHungarian -> Hungarian
                KeyArmenian -> Armenian
                KeyIndonesian -> Indonesian
                KeyIcelandic -> Icelandic
                KeyItalian -> Italian
                KeyJapanese -> Japanese
                KeyGeorgian -> Georgian
                KeyKazakh -> Kazakh
                KeyKorean -> Korean
                KeyKyrgyz -> Kyrgyz
                KeyLithuanian -> Lithuanian
                KeyMaori -> Maori
                KeyMongolian -> Mongolian
                KeyMarathi -> Marathi
                KeyMalay -> Malay
                KeyMaltese -> Maltese
                KeyNorwegian -> Norwegian
                KeyDutch -> Dutch
                KeyNorthernSotho -> NorthernSotho
                KeyPolish -> Polish
                KeyPashto -> Pashto
                KeyPortuguese -> Portuguese
                KeyQuechua -> Quechua
                KeyRomanian -> Romanian
                KeyRussian -> Russian
                KeySlovak -> Slovak
                KeyAlbanian -> Albanian
                KeySwedish -> Swedish
                KeySwahili -> Swahili
                KeyTamil -> Tamil
                KeyTelugu -> Telugu
                KeyTagalog -> Tagalog
                KeyTswana -> Tswana
                KeyTurkish -> Turkish
                KeyTatar -> Tatar
                else -> Other(string)
            }
        }
    }
}
