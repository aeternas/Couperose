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
