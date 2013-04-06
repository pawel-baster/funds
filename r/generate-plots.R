colors = rainbow(4)

plot_best_history <- function() {
  history.best.ranked <- read.table("data/BestRankedMbankExperiment_best_history.csv", sep=";", quote="\"")
  history.full <- read.table("data/MBankFullExperiment_best_history.csv", sep=";", quote="\"")
  history.ward <- read.table("data/WardMBankExperiment_best_history.csv", sep=";", quote="\"")
  history.basic <- read.table("data/MBankExperiment_best_history.csv", sep=";", quote="\"")
  
  maxy = max(c(history.best.ranked[,2], history.full[,2], history.ward[,2], history.basic[,2]))
  
  period = 1:nrow(history.full)
  
  plot(history.best.ranked[period,2], type="l", col=colors[1], ylim=c(0,maxy))
  lines(history.full[period,2], type="l", col=colors[2])
  lines(history.ward[period,2], type="l", col=colors[3])
  lines(history.basic[period,2], type="l", col=colors[4])
  
  legend("topleft", c("best ranked", "full", "ward", "basic"), col=colors, lty=1)
}

plot_experiment_history <- function() {
    
  history.best.ranked <- read.table("data/BestRankedMbankExperiment_experiment_history.csv", sep=";", quote="\"")
  history.full <- read.table("data/MBankFullExperiment_experiment_history.csv", sep=";", quote="\"")
  history.ward <- read.table("data/WardMBankExperiment_experiment_history.csv", sep=";", quote="\"")
  history.basic <- read.table("data/MBankExperiment_experiment_history.csv", sep=";", quote="\"")
  
  sets = list(history.best.ranked, history.full, history.ward, history.basic)
  
  ymin = 2
  ymax = 0
  
  for (i in 1:length(sets)) {
    sets[[i]][, 2] = sets[[i]][, 2]/sets[[i]][1, 2]
    sets[[i]][, 4] = sets[[i]][, 4]/sets[[i]][1, 4]
    
    ymin = min(ymin, min(sets[[i]][, c(2,4)]))
    ymax = max(ymax, max(sets[[i]][, c(2,4)]))
  }
  
  plot(function (x) { 1.03 ^ (x/365) }, type="l", col="black", ylim=c(ymin, ymax), xlim=c(0,length(sets[[i]][,2])), ylab="")
  
  for (i in 1:length(sets)) {
    lines(sets[[i]][,2], col=colors[i], ylim=c(ymin, ymax))
    lines(sets[[i]][,4], col=colors[i], ylim=c(ymin, ymax), lty=2)
  }
}

width = 12
height = 7
bg = "white"

svg(file="data/best_history.png", width=width, height=height)
plot_best_history()
dev.off()

svg(file="data/experiment_history.png", width=width, height=height)
plot_experiment_history()
dev.off()