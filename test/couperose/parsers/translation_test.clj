(ns couperose.parsers.translation-test
  (:require [clojure.test :refer :all]
            [couperose.parsers.translation :as translationParser]
            [couperose.dto.dtos :as dtos]))

(deftest getLanguageGroupsQuery
  (def singleLanguageGroup [(dtos/make-language-group "Turkic" [(dtos/make-language "Tatar" "tt") (dtos/make-language "Turkic" "tr")])])
  (def twoLanguageGroups [(dtos/make-language-group "Turkic" [(dtos/make-language "Tatar" "tt") (dtos/make-language "Turkish" "tr")]) (dtos/make-language-group "Baltic" [(dtos/make-language "Latvian" "lv") (dtos/make-language "Lietuvan" "lt")])])
  (testing "creating language group query from two lang groups"
    (is (= "&group=Turkic&group=Baltic" (translationParser/getLanguageGroupsQuery twoLanguageGroups))))

  (testing "creating language group query from single lang groups"
    (is (= "&group=Turkic" (translationParser/getLanguageGroupsQuery singleLanguageGroup)))))

(deftest getQuery 
  (testing "create full request query"
    (let [someLongPhrase "Do you remember love"]
      (is (= "?translate=Do you remember love&group=Turkic&group=Baltic" (translationParser/getQuery someLongPhrase twoLanguageGroups))))))
