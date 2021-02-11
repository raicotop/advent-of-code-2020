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
          (evaluate ((resolve fst) acc (evaluate 0 snd)) rst)
          (evaluate (evaluate 0 fst) (rest expr)))))))

(defn sol-1
  [s]
  (->> (clojure.string/replace (clojure.string/replace s "(" "[") ")" "]")
       (apply str)
       (#(str "[" % "]"))
       (read-string)
       (evaluate 0)))

(comment
  
  (map sol-1 demo)
  (->> (map sol-1 (clojure.string/split-lines (slurp "resources/input.txt")))
       (reduce +)) ;=> 45283905029161

,)
