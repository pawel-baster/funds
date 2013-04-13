#!/usr/bin/Rscript

colors = rainbow(4)

plot_best_history <- function() {
  history.best.ranked <- read.table("data/BestRankedMbankExperiment_best_history.csv", sep=";", quote="\"")
  history.full <- read.table("data/MBankFullExperiment_best_history.csv", sep=";", quote="\"")
  history.ward <- read.table("data/WardMBankExperiment_best_history.csv", sep=";", quote="\"")
  history.basic <- read.table("data/MBankExperiment_best_history.csv", sep=";", quote="\"")
  
  maxy = max(c(history.best.ranked[,2], history.full[,2], history.ward[,2], history.basic[,2]))
  
  period = 1:nrow(history.full)
  
  plot(history.best.ranked[period,2], type="l", col=colors[1], ylim=c(0,maxy))#, xaxt = "n")
  #labels=history.best.ranked[,1]
  
  #axis(1, at=1:length(history.best.ranked[,1]), labels=history.best.ranked[,1], las=2)
  
  lines(history.full[period,2], type="l", col=colors[2])
  lines(history.ward[period,2], type="l", col=colors[3])
  lines(history.basic[period,2], type="l", col=colors[4])
  
  legend("topleft", c("best ranked", "full", "ward", "basic"), col=colors, lty=1)
}

plot_experiment_history <- function(includeBestHistory) {
    
  history.best.ranked <- read.table("data/BestRankedMbankExperiment_experiment_history.csv", sep=";", quote="\"")
  history.full <- read.table("data/MBankFullExperiment_experiment_history.csv", sep=";", quote="\"")
  history.ward <- read.table("data/WardMBankExperiment_experiment_history.csv", sep=";", quote="\"")
  history.basic <- read.table("data/MBankExperiment_experiment_history.csv", sep=";", quote="\"")
  
  sets = list(history.best.ranked, history.full, history.ward, history.basic)
  
  ymin = 2
  ymax = 0
    
  for (i in 1:length(sets)) {
    sets[[i]][, 2] = sets[[i]][, 2]/sets[[i]][1, 2]
    ymin = min(ymin, min(sets[[i]][,2]))
    ymax = max(ymax, max(sets[[i]][,2]))
    
    if (includeBestHistory) {
      sets[[i]][, 4] = sets[[i]][, 4]/sets[[i]][1, 4]
    
      ymin = min(ymin, min(sets[[i]][,4]))
      ymax = max(ymax, max(sets[[i]][,4]))
    } 
  }
  
  plot(function (x) { 1.02 ^ (x/365) }, type="l", col="black", ylim=c(ymin, ymax), xlim=c(1,length(sets[[i]][,2])), ylab="", xlab="", xaxt = "n")
  axis(1, at=1:length(sets[[i]][,1]), labels=sets[[i]][,1], las=2)
  legend("bottomleft", c("best ranked", "full", "ward", "basic"), col=colors, lty=1)
    
  for (i in 1:length(sets)) {
    lines(sets[[i]][,2], col=colors[i], ylim=c(ymin, ymax), lwd=2)
    if (includeBestHistory)
      lines(sets[[i]][,4], col=colors[i], ylim=c(ymin, ymax), lty=2)
  }
}

width = 12
height = 7

svg(file="data/best_history.svg", width=width, height=height)
plot_best_history()
dev.off()

svg(file="data/experiment_history.svg", width=width, height=height)
plot_experiment_history(F)
dev.off()

svg(file="data/experiment_history_with_best.svg", width=width, height=height)
plot_experiment_history(T)
dev.off()