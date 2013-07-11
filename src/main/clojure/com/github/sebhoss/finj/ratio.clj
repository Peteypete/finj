;   This program is free software. It comes without any warranty, to the extent permitted by applicable law.
;   You can redistribute it and/or modify it under the terms of the Do What The Fuck You Want To Public
;   License, Version 2, as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.

(ns com.github.sebhoss.finj.ratio
  (:require [com.github.sebhoss.finj.def :refer :all]
            [com.github.sebhoss.finj.math :refer :all]))

(defnk debt-ratio 
  "Debt Ratio is a financial ratio that indicates the percentage of a company's assets that are provided via debt.

   Parameters:
     * total-debt   - Sum of current liabilities and long-term liabilities
     * total-assets - Sum of current assets, fixed assets, and other assets such as 'goodwill'

   Examples:
     * (debt-ratio :total-debt 1000 :total-assets 10000)
       => 1/10
     * (debt-ratio :total-debt 500 :total-assets 2000)
       => 1/4
     * (debt-ratio :total-debt 300 :total-assets 2500)
       => 3/25

   References:
     * http://en.wikipedia.org/wiki/Debt_ratio"
  [:total-debt :total-assets]
  {:pre [(number? total-debt)
         (number? total-assets)]}
  (/ total-debt total-assets))

(defnk debt-to-capital-ratio
  "Debt-to-capital ratio or D/C ratio is the ratio of its total debt to its total capital, its debt and equity combined.
   The ratio measures a company's capital structure, financial solvency, and degree of leverage, at a particular point in time.

   Parameters:
     * debt   - Sum of current liabilities and long-term liabilities
     * equity - Sum of current assets, after all liabilities are paid

   Examples:
     * (debt-to-capital-ratio :debt 1000 :equity 10000)
       => 1/11
     * (debt-to-capital-ratio :debt 500 :equity 2000)
       => 1/5
     * (debt-to-capital-ratio :debt 300 :equity 2500)
       => 3/28

   References:
     * http://en.wikipedia.org/wiki/Debt-to-capital_ratio"
  [:debt :equity]
  {:pre [(number? debt)
         (number? equity)]}
  (/ debt (+ debt equity)))

(defnk debt-to-equity-ratio
  "Debt-to-equity ratio (D/E) is a financial ratio indicating the relative proportion of shareholders' equity and debt used to
   finance a company's assets. The ratio is also known as Risk, Gearing or Leverage

   Parameters:
     * debt   - Sum of current liabilities and long-term liabilities
     * equity - Sum of current assets, after all liabilities are paid

   Examples:
     * (debt-to-equity-ratio :debt 1000 :equity 10000)
       => 1/10
     * (debt-to-equity-ratio :debt 500 :equity 2000)
       => 1/4
     * (debt-to-equity-ratio :debt 300 :equity 2500)
       => 3/25

   References:
     * http://en.wikipedia.org/wiki/Debt-to-equity_ratio"
  [:debt :equity]
  {:pre [(number? debt)
         (number? equity)]}
  (/ debt equity))

(defnk debtor-collection-period
  "The debtor collection period indicates the average time taken to collect trade debts.

   Parameters:
     * average-debtor - Debtors at the beginning of the year + debtors at the end of the year, divided by 2
     * credit-sales   - Sales made on credit
     * days           - Number of days (optional, defaults to 365)

   Examples:
     * (debtor-collection-period :average-debtor 1000 :credit-sales 10000)
       => 73/2
     * (debtor-collection-period :average-debtor 500 :credit-sales 2000 :days 360)
       => 90N
     * (debtor-collection-period :average-debtor 300 :credit-sales 2500)
       => 219/5

   References:
     * http://en.wikipedia.org/wiki/Debtor_collection_period"
  [:average-debtor :credit-sales :opt-def :days 365]
  {:pre [(number? average-debtor)
         (number? credit-sales)
         (number? days)]}
  (* days (/ average-debtor credit-sales)))

(defnk current-ratio
  "The current ratio is a financial ratio that measures whether or not a firm has enough resources to pay its debts over the next
   12 months. It compares a firm's current assets to its current liabilities.

   Parameters:
     * current-assets      - Debtors at the beginning of the year + debtors at the end of the year, divided by 2
     * current-liabilities - Sales made on credit

   Examples:
     * (current-ratio :current-assets 1000 :current-liabilities 10000)
       => 1/10
     * (current-ratio :current-assets 500 :current-liabilities 2000)
       => 1/4
     * (current-ratio :current-assets 300 :current-liabilities 2500)
       => 3/25

   References:
     * http://en.wikipedia.org/wiki/Current_ratio"
  [:current-assets :current-liabilities]
  {:pre [(number? current-assets)
         (number? current-liabilities)]}
  (/ current-assets current-liabilities))

(defnk capital-adequacy-ratio
  "Capital Adequacy Ratio (CAR), also called Capital to Risk (Weighted) Assets Ratio (CRAR), is a ratio of a bank's capital to its risk.

   Parameters:
     * tier-1-capital       - (paid up capital + statutory reserves + disclosed free reserves) -
                              (equity investments in subsidiary + intangible assets + current & b/f losses)
     * tier-2-capital       - Undisclosed Reserves + General Loss reserves + hybrid debt capital instruments and subordinated debts
     * risk-weighted-assets - Assets or off-balance sheet exposures, weighted according to risk

   Examples:
     * (capital-adequacy-ratio :tier-1-capital 1000 :tier-2-capital 1200 :risk-weighted-assets 5000)
       => 11/25
     * (capital-adequacy-ratio :tier-1-capital 500 :tier-2-capital 800 :risk-weighted-assets 3200)
       => 13/32
     * (capital-adequacy-ratio :tier-1-capital 300 :tier-2-capital 650 :risk-weighted-assets 1800)
       => 19/36

   References:
     * http://en.wikipedia.org/wiki/Capital_adequacy_ratio"
  [:tier-1-capital :tier-2-capital :risk-weighted-assets]
  {:pre [(number? tier-1-capital)
         (number? tier-2-capital)
         (number? risk-weighted-assets)]}
  (/ (+ tier-1-capital tier-2-capital)
     risk-weighted-assets))

(defnk capital-recovery-factor
  "The capital recovery factor is the ratio of a constant annuity to the present value of receiving that annuity for a given length of time.

   Parameters:
     * rate   - Rate of interest
     * period - Number of periods = number of annuities received

   Examples:
     * (capital-recovery-factor :rate 0.05 :period 5)
       => 0.23097479812826793
     * (capital-recovery-factor :rate 0.1 :period 12)
       => 0.14676331510028723
     * (capital-recovery-factor :rate 0.15 :period 18)
       => 0.16318628735215818

   References:
     * http://en.wikipedia.org/wiki/Capital_recovery_factor"
  [:rate :period]
  {:pre [(number? rate)
         (number? period)]}
  (/ (* rate (pow (inc rate) period))
     (dec (pow (inc rate) period))))

(defnk capitalization-rate
  "The Capitalization rate (or cap rate) is the ratio between the net operating income produced by an asset and its capital cost
   or alternatively its current market value.

   Parameters:
     * income - Annual net operating income
     * cost   - Capital cost (the original price paid to buy the asset)

   Examples:
     * (capitalization-rate :income 1000 :cost 10000)
       => 1/10
     * (capitalization-rate :income 1200 :cost 5000)
       => 6/25
     * (capitalization-rate :income 1800 :cost 3600)
       => 1/2

   References:
     * http://en.wikipedia.org/wiki/Capitalization_rate"
  [:income :cost]
  {:pre [(number? income)
         (number? cost)]}
  (/ income cost))

(defnk equity-ratio
  "The equity ratio is a financial ratio indicating the relative proportion of equity used to finance a company's assets.

   Parameters:
     * equity       - Total shareholder's equity
     * total-assets - Total assets

   Examples:
     * (equity-ratio :equity 1000 :total-assets 10000)
       => 1/10
     * (equity-ratio :equity 1200 :total-assets 5000)
       => 6/25
     * (equity-ratio :equity 1800 :total-assets 3600)
       => 1/2

   References:
     * http://en.wikipedia.org/wiki/Equity_ratio"
  [:equity :total-assets]
  {:pre [(number? equity)
         (number? total-assets)]}
  (/ equity total-assets))
