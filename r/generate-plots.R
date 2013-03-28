history.best.ranked <- read.table("../data/BestRankedMbankExperiment_best_history.csv", sep=";", quote="\"")
history.full <- read.table("../data/MBankFullExperiment_best_history.csv", sep=";", quote="\"")
history.ward <- read.table("../data/WardMBankExperiment_best_history.csv", sep=";", quote="\"")
history.basic <- read.table("../data/MBankExperiment_best_history.csv", sep=";", quote="\"")

maxy = max(c(history.best.ranked[,2], history.full[,2], history.ward[,2], history.basic[,2]))

period = 1:nrow(history.full)

plot(history.best.ranked[period,2], type="l", col="red", ylim=c(0,maxy))
lines(history.full[period,2], type="l", col="blue")
lines(history.ward[period,2], type="l", col="green")
lines(history.basic[period,2], type="l", col="purple")

legend("bottomright", c("best ranked", "full", "ward", "basic"), col=c("red", "blue", "green", "purple"), lty=1)
#axis(1, 1:length(history.full[,2]), labels=history.full[,1])