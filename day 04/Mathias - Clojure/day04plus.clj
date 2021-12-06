(ns aoc-2021.day04plus
  (:require [util :as util]
            [clojure.string :as str]
            [clojure.set :as set]))

(def example-input (list
                     "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1"
                     ""
                     "22 13 17 11  0"
                     " 8  2 23  4 24"
                     "21  9 14 16  7"
                     " 6 10  3 18  5"
                     " 1 12 20 15 19"
                     ""
                     " 3 15  0  2 22"
                     " 9 18 13 17  5"
                     "19  8  7 25 23"
                     "20 11 10 24  4"
                     "14 21 16 12  6"
                     ""
                     "14 21 17 24  4"
                     "10 16 15  9 19"
                     "18  8 23 26 20"
                     "22 11 13  6  5"
                     " 2  0 12  3  7"))
;; PARSE GAME

(defn parse-draw-sequence [line]
  (->> (str/split line #",")
       (map read-string)))

(defn parse-numbers [line]
  (->> (str/split line #"\s+")
       (filter (fn [x] (not (str/blank? x))))               ;; leading and trailing spaces possible
       (map read-string)))

(defn parse-individual-board [rows]
  (->> (map-indexed
         (fn [row-idx row]
           (map-indexed
             (fn [col-idx x]
               (vector x {:x row-idx :y col-idx :marked false}))
             (parse-numbers row)))
         rows)
       (apply concat)
       (into {})))

(defn parse-boards [lines]
  (->> (util/split-using str/blank? lines)
       (map parse-individual-board)
       (into #{})))

(defn parse-game [[draw-seq-line _ & board-lines]]
  (let [draw-sequence (parse-draw-sequence draw-seq-line)
        boards-set (parse-boards board-lines)]
    [draw-sequence boards-set]))

;; PLAY GAME
(defn mark [nb board]
  (let [square-for-nb (board nb)]
    (if square-for-nb
      (assoc-in board [nb :marked] true)
      board)))

(defn mark-each-board [nb boards]
  (->> boards
       (map (fn [b] (mark nb b)))
       (into #{})))

(defn winner? [last-played-nb board]
  (if-let [{impacted-row :x impacted-column :y} (board last-played-nb)]
    (or
      (not-any? (fn [{row :x marked? :marked}] (and (= row impacted-row) (not marked?))) (vals board))
      (not-any? (fn [{column :y marked? :marked}] (and (= column impacted-column) (not marked?))) (vals board)))
    false))

(defn calculate-score [last-played-nb board]
  (let [sum-unmarked (reduce (fn [score [nb {marked? :marked}]]
                               (if (not marked?)
                                 (+ score nb)
                                 score))
                       0
                       board)]
    (* last-played-nb sum-unmarked)))

(defn eliminate-games [[current-nb & next-nbs] boards]
  (let [new-boards (mark-each-board current-nb boards)
        winners (filter (fn [b] (winner? current-nb b)) new-boards)
        pruned-boards (set/difference new-boards winners)]
    (cond
      (not (seq pruned-boards))
      (calculate-score current-nb (first winners))

      (seq next-nbs)
      (recur next-nbs pruned-boards)

      :else
      nil)))

(def input (util/file->seq "2021/d04.txt"))

(defn -main
  "Main function"
  []
  (println (let [[nbs boards] (parse-game input)]
             (eliminate-games nbs boards))))