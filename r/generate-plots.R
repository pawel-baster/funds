history.best.ranked <- read.table("../data/BestRankedMbankExperiment_best_history.csv", sep=";", quote="\"")
history.full <- read.table("../data/MBankFullExperiment_best_history.csv", sep=";", quote="\"")
history.ward <- read.table("../data/WardMBankExperiment_best_history.csv", sep=";", quote="\"")
history.basic <- read.table("../data/MBankExperiment_best_history.csv", sep=";", quote="\"")

maxy = max(c(history.best.ranked[,2], history.full[,2], history.ward[,2], history.basic[,2])

plot(history.best.ranked[,2], type="l", col="red")
lines(history.full[,2], type="l", col="blue")
lines(history.ward[,2], type="l", col="green")
lines(history.basic[,2], type="l", col="purple")