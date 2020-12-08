(ns aoc8.core)

(defn instructions
  [s]
  (->> (clojure.string/split-lines s)
       (map #(clojure.string/split % #" "))
       (map #(vector (first %) (Integer/parseInt (second %))))))

(defn sol
  [instrs]
  (loop [acc 0
         instruction 0
         instructions #{(count instrs)}]
    (if (contains? instructions instruction)
      [acc (>= instruction (count instrs))]
      (let [instruction-current (nth instrs instruction)
            acc-next (if (= "acc" (first instruction-current))
                       (+ acc (second instruction-current))
                       acc)
            instruction-next (if (= "jmp" (first instruction-current))
                               (+ instruction (second instruction-current))
                               (inc instruction))
            instructions-next (conj instructions instruction)]
        (recur acc-next instruction-next instructions-next)))))

(defn sol1
  [s]
  (->> (instructions s)
       (sol)
       (first)))

(defn sol2
  [s]
  (let [instrs (vec (instructions s))]
    (->> (range (count instrs))
         (filter #(contains? #{"jmp" "nop"} (first (nth instrs %))))
         (map #(vector % (if (= "jmp" (first (nth instrs %))) "nop" "jmp")))
         (map #(assoc 
                instrs 
                (first %) 
                (vector (second %) (second (nth instrs (first %))))))
         (map sol)
         (filter #(second %))
         (map first)
         (first))))

(comment 
  (sol1 (slurp "resources/input.txt"))
  (sol2 (slurp "resources/input.txt")))