package funds.funds

import collection.immutable.HashMap
import funds.currencies.CurrencyDKK
import funds.downloaders.Downloader
import funds.ExtendedDate
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/25/12
 * Time: 4:14 PM
 * To change this template use File | Settings | File Templates.
 */
class NordeaFund(
  override val downloader: Downloader,
  override val shortName: String,
  val code1: String, // used for downloading
  val code2: String  // used for downloading
) extends UpdatableFund(shortName, new CurrencyDKK, downloader) {
  var lastUpdate = ExtendedDate.createFromString("1970-01-01")

  def update() = {
    // check if it has not been updated recently
   //if (now - lastUpdate < updateInterval)
    //  return

    //val startDate =
    //val endDate =

    //val fromYear = dateMax.getYear

    /*
    val fromYear =
    val fromMonth =
    val fromDay =

    val toYear =
    val toMonth =
    val toDay =
     */
    val url = "http://www.nordea.dk/PageTemplates/ContentWide.aspx?pid=38647&rw=1&url=/FundsNow/InfoReturn.aspx?isin=" + code1 + "&segment=CustomerDKNB&lang=da-DK&mode=on&shelves=DKNB,DKNB%20DFP&search=on&fundcategorytabs=Focus,All,Equity,Bonds,Mixed,Other,DFP&distribution=1"
    val data = "__VIEWSTATE=%2FwEPDwUKLTMyNTQ3MTI0MQ9kFgJmD2QWAmYPZBYCAgEPFgIeBGxhbmcFBWRhLURLFgICAw8WAh4FY2xhc3MFC2NvbnRlbnR3aWRlFgQCAw8PFgQeCENzc0NsYXNzBQZ0b3BuYXYeBF8hU0ICAmRkAgUPFgIeBmFjdGlvbgWBAmh0dHA6Ly93d3cubm9yZGVhLmRrL1BhZ2VUZW1wbGF0ZXMvQ29udGVudFdpZGUuYXNweD9waWQ9Mzg2NDcmcnc9MSZ1cmw9L0Z1bmRzTm93L0luZm9SZXR1cm4uYXNweD9pc2luPURLMDAxMDI1MDE1OCZzZWdtZW50PUN1c3RvbWVyREtOQiZsYW5nPWRhLURLJm1vZGU9b24mc2hlbHZlcz1ES05CLERLTkIgREZQJnNlYXJjaD1vbiZmdW5kY2F0ZWdvcnl0YWJzPUZvY3VzLEFsbCxFcXVpdHksQm9uZHMsTWl4ZWQsT3RoZXIsREZQJmRpc3RyaWJ1dGlvbj0xFgICAQ9kFgQCAw8PFgIeB1Zpc2libGVoZGQCBQ9kFgICAw8PFgQfAgULQ0JveENvbnRlbnQfAwICZBYCZg9kFgICAQ9kFgRmD2QWAgIBDxYCHglpbm5lcmh0bWwFFkludmVzdGVyaW5nc2FmZGVsaW5nZXJkAgIPFgIfBWhkZA0IJiBU0OZysIRkSHOBlD1HZH9A&__EVENTTARGET=&__EVENTARGUMENT=&__VSTATE=QlpoOTFBWSZTWQf%252bhHAAGOD%252f%252fP%252f%252f%252f%252f%252f3%252f%252f%252fvv%252f%252ff4L%252f%252f%252f%252boCABBAAwAQQAgAAADgDD4n3z17nQns9x2ndZ2OkoCg7yq7VdLsTsFseXBIpNEymNASn4TVPFPaZEn6p%252blPSNP1T1NpBpoPU0bU0eo0PKA8poMm1MJ5QBKmCE00FPSp6nqP1TygNAAeo9IAAaAAAAAAAAAOAAAAAAAAAMhoAAAAAAAAAAEmpU1TTJ%252fpSAAAANAGgABoAAAAAAAAABFJCaTJplD0ahtTTQ0yaGjT0mgANDRoBoNAGgAAD1DQEkEJoJGmU0000aEZqbTSjaZMoyaA9Q08oyAyaeU000D1AYaTR6jdvEhmfVSWmkduKobO090nAT%252fQJK%252fZFQ1p3zDX1cWdZCtuUoYYxGwDrhmGQTiBOhowIgTrMKxDVplNDiDAhOMAmCsBQMCvCZUEwvgxlshQyB4sJIagy8OriIE4gPPLhqmsa56BsF4xmyZDKbRtkyOoMStggVkEIHEwAuSDlckkWLCBwohJFB%252bQ6wtgb8EOACHBBDhAhwwQ4gIcUEMgEMcLZYjgDIBC8ELwS8S8S8S8S8S8S4hYQsIWELCFhCwhYQsIWELCFhCxiyShsUVYoqxRViirFFWKKsUVYoqxRKuSJVyFFcaH55aLaBMmaYAXeQOdkFNwARz73%252bPzdAkXQGBwQgsJOKEgQMJy2ClIEEgSTQAaBGyh31AFUwqjGf1TAiRAQ59k4V4uHjL7%252bPeRNmRX4IpxvEC0pZuOKVoqxhVrRhFcItGBuagDe0W6EsoEl5noxwMa%252b33nO4yXdg5%252fUnLDjtDDMTMloZCmlClJeIZnGhQ7DzEQnHYd0zuzid2RMESxQGJVJIHoM5R1NIHJJgZyaO0UclyiUHcjuYdVpZAa0wIQZkseTqkWyfTm2eWmVGjR1Uc6VmTnbTmWRVjmxnbm3Th8rYRERa1rW4eHm0M0OLQ5xQ3EpBLA4v46NSVann7ux5%252bDhmNvJlyxC7PAza0ng7%252byATFtIIJXHBNMEIGAOIc3xiBByDj7f84H9%252fRpBblrB3DTlg%252frn0oMZq1XjaUOfEQXqRO3s1ox1OGXgNiAXkpl0dKJJLVi1sCYgyQixYCgAsiwFJBRZYxGO05deVOpTl5dW4duJrNuaf5VaVEO6LTXGd8VjGjtjJ3rFFeFdKplUq7UFasUqsMsRObIjMytMQqKLFFZqwhUiio1XWZXCi1pFYGRZoq4Vha%252bqS6SUgQWTCU7UDi4NTo4jq3MGfDuaJK8YNLnseWTSagLYaYxZ7G5hhuEmrCEQuYswr6FMoYlAfQWa6tJUeORUNvSax7wkoiQlbq1793zrR1mWGT49fSCVhaeGiE5%252flFOrLbx7OWmccZLEjH1jMJuvYuM%252bqRUA%252bGY8GyP5xzTfb8Evkp25Envh3q%252fs64%252bLkP5MfZ%252faL87lNMH4e6b%252b9WZNumVTjGfmeTNCNTkeIIWrSHpN0rNCK81Xd4hrPUtZr1vdOwywHbByIeql5JeWvWr2sO156lFSExNV6b%252bLBHqauUV6AC3kmJptjaWjanQT%252bXd6%252bPXPtYXUaZqEDUg70cDlvzSyyQiQDftJOoQCLbawNYKwXY2YC4xpJhjJGoRSFSQYUCQxdOVqDpwuyQ6HZEsUckGbpJgVGgdyWrCCWYICmb0koNMhViGQzBVqjJIpLoiroHKzCIYKw5aqGKjnADBBA50r9PDf8iTskVwxthSTJXf5yS4Nt2SsiIC3wZ0uqznPwQxlyGIOAa%252fioEDAy20xI10BxgZUJot3KlRhgrWqkkJid6ioFFRQkPAVeA8LBs3qhvVvUcFa1pJrSlAmuGSC7QcIZhUQwGBQYmKUvPckaySb6KrXaa7TWzYPrqaJgUUUTQzypKEpIEv3VDXhnpITLg6YNUX3JCiHWbwDQirQDCyFBYUKXcvaD8SNgqaCFkRGBBhpcGIq7C5xDY5vStqvuzMTWlNhbIamXAhPJXB7GbEx6OsKtsGhHlFdzOklsrtsbBU1IGBhhhKl20RYtslqZMyNGqJomKSWhnoKphRSHrW0Tk6va5hfSmgYFSgwMMMY47Yq8EEKTVBWaeY0aIgmi0lnTDQwqskwRS1UUyVVi84SEFZEhsGVNFHqvd6AoxcYGGGISq4LD5IiXd8VrXdtLUOBM2LbpmJZMlkw6dQ4Po8EsswhjXpNNb0vaCsJqDkygwMMMLdcGpBERfJCjYKkUsaqqSvBneC7LULM1EGpak4yByxgMDDDCxSr4k7WrSbzVi8ZICKZZRNEW9Fi6grLjTFWGV7uXSKJkEyZie64OOOPfBKQLCzhrPAuOY9bzVZYVrVyBnIk6q9Luw9mquFRoyyBSxU9PkOgS19DyF%252f28%252bng6fc0Y4oWn8sZfEqZgmz%252bSMSvYpDc3%252fTsZq1%252fuouwk%252bfIVKODPhHpiwKLfzpqYbnuXnTGLWx5krBBwvxJ3MAjhRWQWOZA0wGlHW2kgtcV08mEYIVG0htlUXN2XEOYOT8Iu8%252bXl5OvD8anHJ6nLPy1G4aRO6zZ6Ksu3Us4Fixhph%252fEzEhMgNkFAuAqt0iUWJBppu7eLva1Hjya%252fh6ZayOx9e7J8MueWPYYvR5onfQug%252bLtcEg4a3eKZYqamNogiF1EQ8%252bfGGCMc3UyAvkd5Oh39CpZC9C4kJeUtBrBCCT4ctPFu46vO3jd2PunIef72GM8xSO0tiqXFkz16u5YxJCUaCiku0B%252blUEIOFSf0wG5MQxEGfF5pR493jbMSEpPgc%252bU9n19MgSEeNe2LgEFD9LtQARVLbO5p2cP3CSfhUBJcbQBdy8l6KA7rIDDy1gPAxNj3oCKOHVYIZNrOtucJoCEu87Zg4SwQyJ06jk7f1Nm%252buQc%252fAG65BBBIefW9B6hyiQlIWrOBFQQv94fSdoSEvuGe7pyucBkjMSTIPKkhZPHsNqPrtves7aY4GxGHRix4Oxrq8neqFT5trIIIkxJQiKGNH76WjGVN%252beN10aUc%252fcd76R5RynYvYk4vkAMvPjQgLmUlPGuzCuXCEtULwc8LwXg7ofEH7w8Ad8LhcuXLly5cuXLly5cuXLly5csWLFixYsWLlzwepsOzq753n7YIQb4bEkLKch2H9e7hmi5quJCWXIuDOQQgbD0%252b0zTaRvTNoHBFFwCJitboqEpLEVLIUCiwCIRFTYUGVIzaLAotemN1VrVp6ZTrb35Vc1JX7PAoVWa0icKExISq8VnnVKCSEOrUtl%252btGN97UvbrzYYsmOgsCeDoJqZjHTASUUHJoBNw2%252bIK8vO9ve4tOoDWZszxCSbn7n2%252fQSFQQg53V2cKDKhvzl%252fwNSoUajN00MWSQl9k4t7jvxXckwFwOoXETjVxSS0sJr3BCBzXS1c3%252bN2rP%252bkwRBRmRiNuXgjBxWFYEK%252fT%252b14eFG6uszMmY39zjgp4Q25GVzB2%252fANmL2O5IGMU6lEhZNYMglA1IIAJ6nZwF5XITwhf962TRPpeCptoQxyIWGCIdaQ3CC7WlYRGGMqbM2NtPw8uFRse0JCT9drKiwOuKSw6zQh22j0XUGRTTrYdrCD1FphDZrNJJZYlUSuKh2%252bb0bmOPSN0xXZEl6wkJPTX2IgG7a0xrUK1cSaOZpCTZHYf8vyo1c4DWFsczVBcGVCmMioosUYKisWIpFGKIjBVEFjARVVVVhhhi9AfLoptRfHGo6venkIRt4D9fmNFyYtucXHza0hFYLMEGMkQJKy7VWQsZpa7cpS8GJ8fm%252f7cYkNpLt162hQEt%252fumfe2h6qC9m%252f8XckU4UJAH%252foRwA%253d&__VIEWSTATE_DCReverseProxy=&ctl00%24HiddenFieldSelectedFunds=&ctl00%24HiddenFieldCatID=&ctl00%24HiddenFieldListMain=https%3A%2F%2Fwww.nordea.dk%2FPageTemplates%2FContentWide.aspx%3Fpid%3D38647%26rw%3D1%26url%3D%2FFundsNow%2FListMain.aspx&ctl00%24IsTolboxXrayOpend=false&ctl00%24IsTolboxCompareOpend=false&ctl00%24IsSearchAreaOpend=&ctl00%24FundCategory=&ctl00%24HiddenFundListFilter=&ctl00%24IsWarehouseOpend=&ctl00%24PageHeaderPlaceHolder%24QuickFinderControl%24QuickFindDropDown=" + code1 + "&ctl00%24PageContentPlaceHolder%24performaceGraph%24hidden_ListIsinCodes=" + code1  + "%2C" + code2 + "&ctl00%24PageContentPlaceHolder%24performaceGraph%24hidden_FundsBenchmarkIsinCode=" + code2 + "&ctl00%24PageContentPlaceHolder%24performaceGraph%24hidden_RemovedIsinsFromGraph=&ctl00%24PageContentPlaceHolder%24performaceGraph%24hidden_CompareIsinCode=&ctl00%24PageContentPlaceHolder%24performaceGraph%24hidden_ShowBenchmarkRealName=&ctl00%24PageContentPlaceHolder%24performaceGraph%24hidden_PerformanceGraphPeriod=ThreeYears&ctl00%24PageContentPlaceHolder%24performaceGraph%24hidden_PerformanceGraphStartDate=&ctl00%24PageContentPlaceHolder%24performaceGraph%24hidden_PerformanceGraphEndDate=&ctl00%24PageContentPlaceHolder%24performaceGraph%24BenchmarkDropDown=" + code2 + "&ctl00%24PageContentPlaceHolder%24performaceGraph%24FundDropDown=0&ctl00%24PageContentPlaceHolder%24performaceGraph%24PeriodControl%24FromYearDropDown=2012&ctl00%24PageContentPlaceHolder%24performaceGraph%24PeriodControl%24FromMonthDropDown=1&ctl00%24PageContentPlaceHolder%24performaceGraph%24PeriodControl%24FromDayDropDown=1&ctl00%24PageContentPlaceHolder%24performaceGraph%24PeriodControl%24ToYearDropDown=2012&ctl00%24PageContentPlaceHolder%24performaceGraph%24PeriodControl%24ToMonthDropDown=1&ctl00%24PageContentPlaceHolder%24performaceGraph%24PeriodControl%24ToDayDropDown=1&ctl00%24PageContentPlaceHolder%24ExportPeriod%24FromYearDropDown=2012&ctl00%24PageContentPlaceHolder%24ExportPeriod%24FromMonthDropDown=1&ctl00%24PageContentPlaceHolder%24ExportPeriod%24FromDayDropDown=1&ctl00%24PageContentPlaceHolder%24ExportPeriod%24ToYearDropDown=2012&ctl00%24PageContentPlaceHolder%24ExportPeriod%24ToMonthDropDown=10&ctl00%24PageContentPlaceHolder%24ExportPeriod%24ToDayDropDown=2&ctl00%24PageContentPlaceHolder%24ExportCSVButton.x=9&ctl00%24PageContentPlaceHolder%24ExportCSVButton.y=2"
    val csvFile = downloader.download(url, data)
    csvFile.getLines.drop(1).foreach(l => {
      val cols = l.split(" ")
      val date = ExtendedDate.createFromString(cols(0))
      val dayCount = date.getDayCount()
      quotes = quotes + ((dayCount, cols(1).replace(',', '.').toDouble))
      if (dateMax.isEmpty || (date after dateMax.get)) {
        dateMax = Option(date)
      }
      if (dateMin.isEmpty || (date before dateMin.get)) {
        dateMin = Option(date)
      }
    })
  }
  def calculateSellFee(value: Double): Double = value
  def calculateBuyFee(value: Double): Double = value
}
