(ns com.github.sebhoss.finj.root-finding
  "Root finding algorithms"
  (:require [com.github.sebhoss.finj.math :refer :all]))

(defrecord SearchResult [estimate estimation-error number-of-iterations first-value second-value])

(defn iter-root-search
  "Iterative search for a root using two values as inputs."
  [& {:keys [^IFn function ^Number first-value ^Number second-value ^Number tolerance
             ^Number max-iterations ^IFn next-estimate ^IFn next-first ^IFn next-second]
      :or {tolerance 0.00001
           max-iterations 100}}]
  {:pre [(pos? tolerance)
         (pos? max-iterations)]
   :post [(< (abs (:estimation-error %)) tolerance)
          (pos? (:number-of-iterations %))]}
  (loop [first-value first-value
         second-value second-value
         iteration 1]
    (if (<= iteration max-iterations)
      (let [estimate (next-estimate first-value second-value)
            estimation-error (function estimate)
            result (SearchResult. estimate estimation-error iteration first-value second-value)]
        (if (< (abs estimation-error) tolerance)
          result
          (recur (next-first result) (next-second result) (inc iteration))))
      (throw (IllegalArgumentException. "max number of iterations exceeded - could not satisfy tolerance")))))

(defn bisect
  "Root-finding method which repeatedly bisects an interval and then selects a subinterval in which a root must lie for
   further processing. It is a very simple and robust method, but it is also relatively slow. Because of this, it is
   often used to obtain a rough approximation to a solution which is then used as a starting point for more rapidly
   converging methods. The method is also called the binary search method or the dichotomy method.

   Parameters:
     * function         - The function to apply to each possible root.
     * lower-startpoint - The lower bound of the initial search-interval.
     * upper-startpoint - The upper bound of the initial search-interval.
     * tolerance        - The maximum tolerance allowed for a possible root to become a root.
     * max-iterations   - The maximum numbers of iterations to run before throwing an exception.

   References:
     * http://en.wikipedia.org/wiki/Bisection_method"
  [& {:keys [function lower-startpoint upper-startpoint tolerance max-iterations]
      :or {tolerance 0.00001
           max-iterations 100}}]
  {:pre [(< lower-startpoint upper-startpoint)
         (sgn-different? (function lower-startpoint) (function upper-startpoint))]
   :post [(< (:first-value %) (:second-value %))]}
  (iter-root-search
    :function function
    :first-value lower-startpoint
    :second-value upper-startpoint
    :tolerance tolerance
    :max-iterations max-iterations
    :next-estimate (fn [first-value second-value]
                     (mean first-value second-value))
    :next-first (fn [result]
                  (if (pos? (:estimation-error result))
                    (:first-value result)
                    (:estimate result)))
    :next-second (fn [result]
                   (if (pos? (:estimation-error result))
                     (:estimate result)
                     (:second-value result)))
    ))

(defn secant
  "The secant method is a root-finding algorithm that uses a succession of roots of secant lines to better approximate
   a root of a function f.

   References:
     * http://en.wikipedia.org/wiki/Secant_method"
  [& {:keys [function first second tolerance max-iterations]
      :or {tolerance 0.00001
           max-iterations 100}}]
  (iter-root-search
    :function function
    :first-value first
    :second-value second
    :tolerance tolerance
    :max-iterations max-iterations
    :next-estimate (fn [first-value second-value]
                     (let [fa (function first-value)
                           fb (function second-value)]
                       (- first-value (* (/ (- first-value second-value)
                                            (- fa fb))
                                         fa))))
    :next-first (fn [result]
                  (:estimate result))
    :next-second (fn [result]
                   (:first-value result))
    ))

(defn newton
  "The newton method is a method for finding successively better approximations to the roots (or zeroes) of a
   real-valued function.

   References:
     * http://en.wikipedia.org/wiki/Newton%27s_method"
  [& {:keys [function derivative min-denominator start-value tolerance max-iterations]
      :or {tolerance 0.00001
           max-iterations 100}}]
  (iter-root-search 
    :function function
    :first-value start-value
    :second-value nil
    :tolerance tolerance
    :max-iterations max-iterations
    :next-estimate (fn [first-value second-value]
                     (let [denominator (derivative first-value)]
                       (if (< (abs denominator) min-denominator)
                         (throw (IllegalArgumentException. "denominator too small"))
                         (- first-value (/ (function first-value)
                                           denominator)))))
    :next-first (fn [result] (:estimate result))
    :next-second (fn [result] nil)
    ))

(defn regula-falsi
  "The false position method or regula falsi method is a term for problem-solving methods in arithmetic, algebra, and
   calculus.

   References:
     * http://en.wikipedia.org/wiki/False_position_method"
  [& {:keys [function lower-startpoint upper-startpoint tolerance max-iterations]
      :or {tolerance 0.00001
           max-iterations 100}}]
  {:pre [(< lower-startpoint upper-startpoint)
         (sgn-different? (function lower-startpoint) (function upper-startpoint))]
   :post [(< (:first-value %) (:second-value %))]}
  (iter-root-search
    :function function
    :first-value lower-startpoint
    :second-value upper-startpoint
    :tolerance tolerance
    :max-iterations max-iterations
    :next-estimate (fn [first-value second-value]
                     (let [fa (function first-value)
                           fb (function second-value)]
                       (- first-value (/ (* fa (- second-value first-value))
                              (- fb fa)))))
    :next-first (fn [result]
                  (if (pos? (:estimation-error result))
                    (:first-value result)
                    (:estimate result)))
    :next-second (fn [result]
                   (if (pos? (:estimation-error result))
                     (:estimate result)
                     (:second-value result)))
    ))
