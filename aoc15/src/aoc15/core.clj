(ns aoc15.core)

(def demo [0,3,6])

(def my-task [10,16,6,0,1,17])

(defn sol
 [input final-turn]
 (loop [history (->> (vector input (map vector (range (count input))))
                     (apply zipmap))
        last-number (last input)
        turn (count input)]
   (if (= final-turn turn)
     last-number
     (let [last-number-history (get history last-number)
           next-number (if (= 1 (count last-number-history))
                         0
                         (- (last last-number-history) (second (reverse last-number-history))))
           next-history (assoc history next-number (if (contains? history next-number)
                                                     (vector (last (get history next-number)) turn)
                                                     [turn]))]
       (recur next-history next-number (inc turn))))))

(comment
  
  (sol demo 2020)
  (sol my-task 2020) ;=> 412

  (sol demo 30000000)
  (sol my-task 30000000) ;=> 243

  ,)
