(ns aoc18.core)

(def demo 
  ["1 + 2 * 3 + 4 * 5 + 6"
   "1 + (2 * 3) + (4 * (5 + 6))"
   "2 * 3 + (4 * 5)"
   "5 + (8 * 3 + 9 + 3 * 4 * 3)"
   "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"
   "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"])

(defn evaluate
  [acc expr]
  (if (number? expr)
    expr
    (if (empty? expr)
      acc
      (let [fst (first expr)
            snd (second expr)
            rst (drop 2 expr)]
        (if (symbol? fst)
          (evaluate ((eval fst) acc (evaluate 0 snd)) rst)
          (evaluate (evaluate 0 fst) (rest expr)))))))

(defn sol-1
  [s]
  (->> (clojure.string/replace (clojure.string/replace s "(" "[") ")" "]")
       (apply str)
       (#(str "[" % "]"))
       (read-string)
       (evaluate 0)))

(defn evaluate-2
  [acc expr]
  (if (number? expr)
    expr
    (if (empty? expr)
      acc
      (let [fst (first expr)
            snd (second expr)
            trd (nth expr 2 nil)
            fth (nth expr 3 nil)]
        (if (symbol? fst)
          (if (and (symbol? trd)
                   (= * (eval fst))
                   (= + (eval trd)))
            (evaluate-2 acc (concat (vector fst (+ (evaluate-2 0 snd) (evaluate-2 0 fth))) (drop 4 expr)))
            (evaluate-2 ((eval fst) acc (evaluate-2 0 snd)) (drop 2 expr)))
          (evaluate-2 (evaluate-2 0 fst) (rest expr)))))))

(defn sol-2
  [s]
  (->> (clojure.string/replace (clojure.string/replace s "(" "[") ")" "]")
       (apply str)
       (#(str "[" % "]"))
       (read-string)
       (evaluate-2 0)))

(comment
  
  (map sol-1 demo)
  (->> (map sol-1 (clojure.string/split-lines (slurp "resources/input.txt")))
       (reduce +)) ;=> 45283905029161
  
  (map sol-2 demo)
  (->> (map sol-2 (clojure.string/split-lines (slurp "resources/input.txt")))
       (reduce +)) ;=> 216975281211165

,)
