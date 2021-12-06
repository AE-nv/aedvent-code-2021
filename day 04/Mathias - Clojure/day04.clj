(ns aoc-2021.day04
  (:require [util :as util]
            [clojure.string :as str]))

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

(defn play-game [[current-nb & next-nbs] boards]
  (let [new-boards (mark-each-board current-nb boards)
        winners (filter (fn [b] (winner? current-nb b)) new-boards)]
    (cond
      (first winners)
      (calculate-score current-nb (first winners))

      (seq next-nbs)
      (play-game next-nbs new-boards)

      :else
      nil)))

(comment "repl tests"
  (def example-draw-sequence (->> (str/split (first example-input) #",")
                                  (map read-string)))

  (->> (map-indexed (fn [idx x] [x [0 idx]]) (list 10 11 12 13 14 15))
       (into {}))

  (add-to-board {1 [1 1]} 0 (list 10 11 12 13 14 15))

  (parse-boards (rest (rest example-input)))
  (parse-individual-board (list "22 13 17 11  0" " 8  2 23  4 24" "21  9 14 16  7" " 6 10  3 18  5" " 1 12 20 15 19"))
  (parse-game example-input)

  (def board-one {0 {:x 0, :y 4, :marked false},
                  7 {:x 2, :y 4, :marked false},
                  20 {:x 4, :y 2, :marked false},
                  1 {:x 4, :y 0, :marked false},
                  24 {:x 1, :y 4, :marked false},
                  4 {:x 1, :y 3, :marked false},
                  15 {:x 4, :y 3, :marked false},
                  21 {:x 2, :y 0, :marked false},
                  13 {:x 0, :y 1, :marked false},
                  22 {:x 0, :y 0, :marked false},
                  6 {:x 3, :y 0, :marked false},
                  17 {:x 0, :y 2, :marked false},
                  3 {:x 3, :y 2, :marked false},
                  12 {:x 4, :y 1, :marked false},
                  2 {:x 1, :y 1, :marked false},
                  23 {:x 1, :y 2, :marked false},
                  19 {:x 4, :y 4, :marked false},
                  11 {:x 0, :y 3, :marked false},
                  9 {:x 2, :y 1, :marked false},
                  5 {:x 3, :y 4, :marked false},
                  14 {:x 2, :y 2, :marked false},
                  16 {:x 2, :y 3, :marked false},
                  10 {:x 3, :y 1, :marked false},
                  18 {:x 3, :y 3, :marked false},
                  8 {:x 1, :y 0, :marked false}})

  (def mini-board {0 {:x 0, :y 0, :marked true},
                   7 {:x 0, :y 1, :marked true},
                   20 {:x 0, :y 2, :marked false},
                   1 {:x 1, :y 0, :marked false},
                   24 {:x 1, :y 1, :marked false},
                   4 {:x 1, :y 2, :marked false},
                   15 {:x 2, :y 0, :marked false},
                   21 {:x 2, :y 1, :marked true},
                   13 {:x 2, :y 2, :marked false}})

  (winner? 20 (mark 20 mini-board))
  (winner? 24 (mark 24 mini-board))

  (def nbs (first (parse-game example-input)))
  ;; (7 4 9 5 11 17 23 2 0 14 21 24 10 16 13 6 15 25 12 22 18 20 8 19 3 26 1)
  (def boards (second (parse-game example-input)))

  (def just-before-win (->> (mark-each-board 7 boards)
        (mark-each-board 4)
        (mark-each-board 9)
        (mark-each-board 5)
        (mark-each-board 11)
        (mark-each-board 17)
        (mark-each-board 23)
        (mark-each-board 2)
        (mark-each-board 0)
        (mark-each-board 14)
        (mark-each-board 21)
        ;;(mark-each-board 24)
        ))


  (let [[nbs boards] (parse-game example-input)]
    (play-game nbs boards))
  ,)


(def input (util/file->seq "2021/d04.txt"))

(defn -main
  "Main function"
  []
  (println (let [[nbs boards] (parse-game input)]
             (play-game nbs boards))))