(ns couperose.core
  (:require [couperose.services.cacheWarmer :as warmer])
  (:gen-class))

(defn -main
  [& args]
  (warmer/warmCache))
