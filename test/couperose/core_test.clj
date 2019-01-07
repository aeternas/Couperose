(ns couperose.core-test
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json]
            [couperose.core :refer :all]
            [couperose.parsers.language :as languageParser]
            [couperose.dto.dtos :as dtos]))

(deftest LanguageParsingTest
  (def expectedTatarLang (dtos/make-language "Tatar" "tt"))
  (def expectedTurkishLang (dtos/make-language "Turkish" "tr"))
  (def jsonString "{\"fullName\":\"Tatar\", \"code\":\"tt\"}")
  (def arrayJsonString "[{\"fullName\":\"Tatar\", \"code\":\"tt\"}, {\"fullName\":\"Turkish\", \"code\":\"tr\"}]")
  (def languageGroupJsonString "{\"languages\": [{\"fullName\":\"Tatar\", \"code\":\"tt\"}, {\"fullName\":\"Turkish\", \"code\":\"tr\"}], \"name\": \"Turkic\"}")
  
  (testing "parsing with parser"
    (def parsedLanguage (languageParser/parseLanguage jsonString))
    (is (= parsedLanguage expectedTatarLang)))
  
  (testing "parse array of languages"
    (def parsedLanguages (languageParser/parseLanguageArray arrayJsonString))
    (is (= expectedTatarLang (parsedLanguages 0)))
    (is (= expectedTurkishLang (parsedLanguages 1))))

  (testing "parse language group"
    (def parsedLanguageGroup (languageParser/parseLanguageGroupArray languageGroupJsonString))
    (is (= "Turkic" (:name parsedLanguageGroup)))
    (is (= expectedTurkishLang ((:languages parsedLanguageGroup) 1)))))
