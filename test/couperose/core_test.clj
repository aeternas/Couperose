(ns couperose.core-test
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json]
            [couperose.core :refer :all]
            [couperose.services.cacheWarmer :as warmer]
            [couperose.parsers.language :as languageParser]
            [couperose.parsers.translation :as translationParser]
            [couperose.dto.dtos :as dtos]
            [clj-http.client :as c])
  (:use clj-http.fake))

(deftest LanguageParsingTest
  (def expectedTatarLang (dtos/make-language "Tatar" "tt"))
  (def expectedTurkishLang (dtos/make-language "Turkish" "tr"))
  (def jsonString "{\"fullName\":\"Tatar\", \"code\":\"tt\"}")
  (def arrayJsonString "[{\"fullName\":\"Tatar\", \"code\":\"tt\"}, {\"fullName\":\"Turkish\", \"code\":\"tr\"}]")
  (def languageGroupJsonString "{\"languages\": [{\"fullName\":\"Tatar\", \"code\":\"tt\"}, {\"fullName\":\"Turkish\", \"code\":\"tr\"}], \"name\": \"Turkic\"}")
  (def languageGroupArrayJsonString "[{\"languages\": [{\"fullName\":\"Latvian\", \"code\":\"lv\"}, {\"fullName\":\"Lietuvan\", \"code\":\"lt\"}], \"name\": \"Baltic\"}, {\"languages\": [{\"fullName\":\"Tatar\", \"code\":\"tt\"}, {\"fullName\":\"Turkish\", \"code\":\"tr\"}], \"name\": \"Turkic\"}]")
  
  (testing "parsing with parser"
    (def parsedLanguage (languageParser/parseLanguage jsonString))
    (is (= parsedLanguage expectedTatarLang)))
  
  (testing "parse array of languages"
    (def parsedLanguages (languageParser/parseLanguageArray arrayJsonString))
    (is (= expectedTatarLang (parsedLanguages 0)))
    (is (= expectedTurkishLang (parsedLanguages 1))))

  (testing "parse language group"
    (def parsedLanguageGroup (languageParser/parseLanguageGroup languageGroupJsonString))
    (is (= "Turkic" (:name parsedLanguageGroup)))
    (is (= expectedTurkishLang ((:languages parsedLanguageGroup) 1))))


  (testing "parse language group array"
    (def parsedLanguageGroupArray (languageParser/parseLanguageGroupArray languageGroupArrayJsonString))
    (is (= "Lietuvan" (:fullName((:languages (parsedLanguageGroupArray 0)) 1))))))

(deftest TranslationQueryBuildingTest
  (def singleLanguageGroup [(dtos/make-language-group "Turkic" [(dtos/make-language "Tatar" "tt") (dtos/make-language "Turkic" "tr")])])
  (def twoLanguageGroups [(dtos/make-language-group "Turkic" [(dtos/make-language "Tatar" "tt") (dtos/make-language "Turkish" "tr")]) (dtos/make-language-group "Baltic" [(dtos/make-language "Latvian" "lv") (dtos/make-language "Lietuvan" "lt")])])
  (testing "creating language group query from single lang groups"
    (is (= "&group=Turkic" (translationParser/getLanguageGroupsQuery singleLanguageGroup))))

  (testing "creating language group query from two lang groups"
    (is (= "&group=Turkic&group=Baltic" (translationParser/getLanguageGroupsQuery twoLanguageGroups))))

  (testing "create full request query"
    (let [someLongPhrase "Do you remember love"]
      (is (= "?translate=Do you remember love&group=Turkic&group=Baltic" (translationParser/getQuery someLongPhrase twoLanguageGroups))))))

(deftest TranslationRequestTest
  (testing "http request to groups"
    (with-redefs [warmer/groupsUrl "https://test.com/v1/groups"]
                       (def body "{\"languages\": [{\"fullName\":\"Latvian\", \"code\":\"lv\"}]}")
                       (with-fake-routes {
                                          "https://test.com/v1/groups" (fn [request] {:status 200 :headers {} :body body})}
                         (let [groups (future (Thread/sleep 1000) (c/get warmer/groupsUrl))]
                           (is (= body (:body @groups))))))))
