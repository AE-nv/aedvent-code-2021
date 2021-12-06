(ns util
  (:require [clojure.java.io :as io]))

(defn to-int [str]
  (Integer/parseInt (re-find #"\A-?\d+" str)))

(defn file->seq [file-name]
  (->> file-name
       io/resource
       io/reader
       line-seq))

(defn split-using [predicate col]
  (filter (fn [xs] (not (predicate (first xs)))) (partition-by predicate col)))

(defn abs [^long x]
  (Math/abs x))

(defn normalize [xs]
  (->> (map (fn [x]
              (if (= x 0) 0 (/ x (abs x))))
         xs)
       (into [])))