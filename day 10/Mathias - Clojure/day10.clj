(ns aoc-2021.day10
  (:require [util :as util]
            [clojure.set :as set]))

(def example-input (list
                     "[({(<(())[]>[[{[]{<()<>>"
                     "[(()[<>])]({[<{<<[]>>("
                     "{([(<{}[<>[]}>{[]{[(<()>"
                     "(((({<>}<{<{<>}{[]{[]{}"
                     "[[<[([]))<([[{}[[()]]]"
                     "[{[{({}]{}}([{[{{{}}([]"
                     "{<[[]]>}<{[{[{[]{()[[[]"
                     "[<(<(<(<{}))><([]([]()"
                     "<{([([[(<>()){}]>(<<{{"
                     "<{([{{}}[<[[[<>{}]]]>[]]"))

(def error-score-by-char {\) 3
                          \] 57
                          \} 1197
                          \> 25137})
(def chunk-start->end {\( \)
                       \[ \]
                       \{ \}
                       \< \>})
(def chunk-end->start (set/map-invert chunk-start->end))
(defn opens-chunk? [char]
  (contains? chunk-start->end char))
(defn closes-chunk? [char]
  (contains? chunk-end->start char))
(defn matches-current-chunk? [stack char]
  (boolean (= (first stack) (chunk-end->start char))))

(defn scan-chunks-and-score-errors [line]
  (let [[x & rest] (reduce (fn [stack x]
                                 (cond
                                   (opens-chunk? x) (conj stack x)
                                   (and (closes-chunk? x) (matches-current-chunk? stack x)) (rest stack)
                                   :else (reduced (conj stack x)))) ;; done put error char on stack
                         '()
                         line)]
    (error-score-by-char x)))

(comment "repls tests"
  (def line (first example-input))

  (opens-chunk? \[)
  (matches-current-chunk? (list \( \[) \))

  (reduce (fn [stack x]
            (println (str "stack" stack " char " x " closes " (closes-chunk? x) " matches " (matches-current-chunk? stack x)))
            (cond
              (opens-chunk? x) (conj stack x)
              (and (closes-chunk? x) (matches-current-chunk? stack x)) (rest stack)
              :else (reduced (conj stack x))))              ;; done put error char on stack
    '()
    "{([(<{}[<>[]}>{[]{[(<()>")

  (scan-chunks-and-score-errors "{([(<{}[<>[]}>{[]{[(<()>")
  (scan-chunks-and-score-errors "[[<[([]))<([[{}[[()]]]")
  (scan-chunks-and-score-errors "[{[{({}]{}}([{[{{{}}([]")

  (->> example-input
       (map scan-chunks-and-score-errors)
       (filter identity)
       (reduce +))
  ,)


(defn -main
  "Main function"
  []
  (println (->> (util/file->seq "2021/d10.txt")
                (map scan-chunks-and-score-errors)
                (filter identity)
                (reduce +))))