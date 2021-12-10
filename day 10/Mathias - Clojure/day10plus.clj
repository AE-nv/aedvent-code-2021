(ns aoc-2021.day10plus
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

(def completion-score {\) 1
                       \] 2
                       \} 3
                       \> 4})
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

(defn complete [stack]
  (map chunk-start->end stack))

(defn scan-chunks-and-determine-correct-closing [line]
  (let [[x & _ :as stack] (reduce (fn [stack x]
                                 (cond
                                   (opens-chunk? x) (conj stack x)
                                   (and (closes-chunk? x) (matches-current-chunk? stack x)) (rest stack)
                                   :else (reduced (conj stack x)))) ;; done put error char on stack
                         '()
                         line)]
    (if (and x (opens-chunk? x))
      (complete stack)
      '())))

(defn score [closing]
  (reduce (fn [tot x] (+ (* tot 5) (completion-score x)))
    0
    closing))

(comment "repls tests"
  (def line (first example-input))

  (apply str (map chunk-start->end (reverse "[({([[{{")))

  (score (list \] \) \} \>))

  (->> example-input
       (map scan-chunks-and-determine-correct-closing)
       (filter seq)
       (map score)
       sort
       (into []))


  ,)


(defn -main
  "Main function"
  []
  (println (let [sorted-scores (->> (util/file->seq "2021/d10.txt")
                                    (map scan-chunks-and-determine-correct-closing)
                                    (filter seq)
                                    (map score)
                                    sort
                                    (into []))
                 middle-element-idx (quot (count sorted-scores) 2)]
             (get sorted-scores middle-element-idx))))