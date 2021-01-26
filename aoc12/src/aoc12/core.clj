(ns aoc12.core)

(def task "F10
N3
F7
R90
F11")

(defn move
  [[[x y] direction] [instruction value]]
  (condp = instruction
    \E [[(+ x value) y] direction]
    \W [[(- x value) y] direction]
    \N [[x (+ y value)] direction]
    \S [[x (- y value)] direction]
    \L [[x y] (mod (- direction value) 360)]
    \R [[x y] (mod (+ direction value) 360)]
    \F [(condp = direction
          0 [(+ x value) y]
          90 [x (- y value)]
          180 [(- x value) y]
          270 [x (+ y value)]) 
        direction]))

(defn sol
  [[init-x init-y] init-angle init-instructions]
  (loop [x init-x
         y init-y
         angle init-angle
         instructions init-instructions]
    (if (empty? instructions)
      [x y]
      (let [[[new-x new-y] new-angle] (move [[x y] angle] (first instructions))]
        (recur new-x new-y new-angle (rest instructions))))))
  

(defn sol1
  [s]
  (->> (clojure.string/split-lines s)
       (map #(vector (first %) (Integer/parseInt (subs % 1))))
       (sol [0 0] 0)
       (map #(Math/abs %))
       (reduce +)))

(defn rotate
 [angle x y]
 (condp = angle
   0 [x y]
   90 [y (- x)]
   180 [(- x) (- y)]
   270 [(- y) x]))

(defn move-2
  [[[x y] [way-x way-y]] [instruction value]]
  (condp = instruction
    \E [[x y] [(+ way-x value) way-y]]
    \W [[x y] [(- way-x value) way-y]]
    \N [[x y] [way-x (+ way-y value)]]
    \S [[x y] [way-x (- way-y value)]]
    \L [[x y] (rotate (mod (- value) 360) way-x way-y)]
    \R [[x y] (rotate (mod value 360) way-x way-y)]
    \F [[(+ x (* way-x value)) (+ y (* way-y value))] [way-x way-y]]))

(defn sol-2
  [[init-x init-y] [init-way-x init-way-y] init-instructions]
  (loop [x init-x
         y init-y
         way-x init-way-x
         way-y init-way-y
         instructions init-instructions]
    (if (empty? instructions)
      [x y]
      (let [[[new-x new-y] [new-way-x new-way-y]] (move-2 [[x y] [way-x way-y]] (first instructions))]
        (recur new-x new-y new-way-x new-way-y (rest instructions))))))

(defn sol2
  [s]
  (->> (clojure.string/split-lines s)
       (map #(vector (first %) (Integer/parseInt (subs % 1))))
       (sol-2 [0 0] [10 1])
       (map #(Math/abs %))
       (reduce +)))

(sol1 task)
(sol1 (slurp "resources/input.txt")) ;=> 2847

(sol2 task)
(sol2 (slurp "resources/input.txt")) ;=> 29839