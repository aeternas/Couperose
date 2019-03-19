(ns couperose.services.cacheWarmer-test
  (:require [clojure.test :refer :all]
            [couperose.services.cacheWarmer :as warmer]
            [clj-http.client :as c])
  (:use clj-http.fake))


(deftest TranslationRequestTest
  (testing "http request to groups"
    (with-redefs [warmer/groupsUrl "https://test.com/v1/groups"]
                       (def body "{\"languages\": [{\"fullName\":\"Latvian\", \"code\":\"lv\"}]}")
                       (with-fake-routes {
                                          "https://test.com/v1/groups" (fn [request] {:status 200 :headers {} :body body})}
                         (let [groups (future (Thread/sleep 1000) (c/get warmer/groupsUrl))]
                           (is (= body (:body @groups))))))))
