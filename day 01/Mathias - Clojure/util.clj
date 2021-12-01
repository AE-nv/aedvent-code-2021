(ns util
  (:require [clojure.java.io :as io]))

(defn to-int [str]
  (Integer/parseInt (re-find #"\A-?\d+" str)))

(defn file->seq [file-name]
  (->> file-name
       io/resource
       io/reader
       line-seq))