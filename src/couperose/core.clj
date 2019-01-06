(ns couperose.core
  (:require [couperose.services.retriever :as retriever])
  (:gen-class))

(defn -main
  [& args]
  (retriever/retrieve))
