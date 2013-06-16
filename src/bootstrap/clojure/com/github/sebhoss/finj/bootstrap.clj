(require '[com.github.sebhoss.finj.common :as common])
(require '[com.github.sebhoss.finj.compound-interest :as compound-interest])
(require '[com.github.sebhoss.finj.def :as def])
(require '[com.github.sebhoss.finj.deprecation :as deprecation])
(require '[com.github.sebhoss.finj.interest :as interest])
(require '[com.github.sebhoss.finj.investment :as investment])
(require '[com.github.sebhoss.finj.loan :as loan])
(require '[com.github.sebhoss.finj.math :as math])
(require '[com.github.sebhoss.finj.pension :as pension])
(require '[com.github.sebhoss.finj.periodic-payment :as periodic-payment])
(require '[com.github.sebhoss.finj.predicate :as predicate])
(require '[com.github.sebhoss.finj.ratio :as ratio])
(require '[com.github.sebhoss.finj.root-finding :as root-finding])
(require '[com.github.sebhoss.finj.share-price :as share-price])

(require '[clojure.repl :refer :all])
(require '[clojure.test :as test])

(defn rat
  "[R]uns-[A]ll-[T]ests inside the whole project or in a separate namespace.

   Examples:
     * (rat)          - Run all tests in all project namespaces
     * (rat \"common\") - Run all tests in the common/-namespace
     * (rat \"math\")   - Run all tests in the math/-namespace"
  ([] (test/run-all-tests #"com.github.sebhoss.finj.*-test"))
  ([namespace] (test/run-all-tests
                 (re-pattern (format "com.github.sebhoss.finj.%s-test"
                                     namespace)))))
