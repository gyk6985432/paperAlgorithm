package paper.predict;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import paper.dataProcess.LoadRange;
import paper.utils.Constant;

/**
 * Created by gyk on 2016/10/14.
 */
public class Prediction {
    LoadRange loadRange = new LoadRange();
    int[] maxLoads,minLoads;

    public Prediction(){
        getRange();
    }

    private void getRange(){
        maxLoads = loadRange.getMaxLoads();
        minLoads = loadRange.getMinLoads();
    }

    private int[] buildConnection(){
        int[] predicts = new int[2];
        try {
            RConnection rconn = new RConnection();
            rconn.eval("library(Rserve)");
            //> D1=ts(demand1) D2=ts(demand2)
            rconn.assign("maxLoads",maxLoads);
            rconn.assign("minLoads",minLoads);
            rconn.eval("tsmax <- ts(maxLoads)");
            rconn.eval("tsmin <- ts(minLoads)");

            //  画出ACF和PACF
            //> ACF1= acf(D1,plot = TRUE)   ACF2= acf(D2,plot = TRUE)
            //> PACF1= pacf(D1,plot = TRUE)   PACF2= pacf(D2,plot = TRUE)
            rconn.eval("png(filename=\""+Constant.imagePath+"acfmax.png\")");
            rconn.eval("acf(tsmax, plot=TRUE)");
            rconn.eval("dev.off()");

            rconn.eval("png(filename=\""+Constant.imagePath+"acfmin.png\")");
            rconn.eval("acf(tsmin, plot=TRUE)");
            rconn.eval("dev.off()");

            rconn.eval("png(filename=\""+Constant.imagePath+"pacfmax.png\")");
            rconn.eval("pacf(tsmax, plot=TRUE)");
            rconn.eval("dev.off()");

            rconn.eval("png(filename=\""+Constant.imagePath+"pacfmin.png\")");
            rconn.eval("pacf(tsmin, plot=TRUE)");
            rconn.eval("dev.off()");

            //用auto.arima来识别模型
            rconn.eval("library(forecast)");
            //源码forecast_7.3\forecast\R\newarima2.R
            rconn.eval("arimaMax = auto.arima(tsmax,trace=TRUE)");
            REXP arimaMax = rconn.eval("forecast:::arima.string(arimaMax, padding=TRUE)");
            System.out.println("best Max model: " + arimaMax.asString());
            rconn.eval("arimaMin = auto.arima(tsmin,trace=TRUE)");
            REXP arimaMin = rconn.eval("forecast:::arima.string(arimaMin, padding=TRUE)");
            System.out.println("best Min model: " + arimaMin.asString());

            //预测f1=forecast.Arima(D1.ar,h=5),源码forecast_7.3\forecast\R\arima.R
            rconn.eval("fMax = forecast.Arima(arimaMax,h=5)");
            rconn.eval("png(filename=\""+Constant.imagePath+"forecastMax.png\")");
            rconn.eval("plot(fMax)");
            rconn.eval("dev.off()");
            REXP fMax = rconn.eval("fMax$mean");
            System.out.println(fMax.toDebugString());
            System.out.println(fMax.asString());//预测的负荷最大值
            predicts[0] = Integer.valueOf(fMax.asInteger());
            rconn.eval("fMin = forecast.Arima(arimaMin,h=5)");
            rconn.eval("png(filename=\""+Constant.imagePath+"forecastMin.png\")");
            rconn.eval("plot(fMin)");
            rconn.eval("dev.off()");
            REXP fMin = rconn.eval("fMin$mean");
            System.out.println(fMin.toDebugString());
            System.out.println(fMin.asString());//预测的负荷最小值
            predicts[1] = Integer.valueOf(fMin.asInteger());

            //残差检验Box.test(f1$residuals,type = "Ljung-Box")
            rconn.eval("testMax = Box.test(fMax$residuals,type = \"Ljung-Box\")");
            REXP testMax = rconn.eval("testMax$p.value");
            System.out.println(testMax.asString());
            rconn.eval("testMin = Box.test(fMin$residuals,type = \"Ljung-Box\")");
            REXP testMin = rconn.eval("testMin$p.value");
            System.out.println(testMin.asString());
            rconn.close();
        } catch (RserveException e) {
            e.printStackTrace();
        } catch (REngineException e) {
            e.printStackTrace();
        } catch (REXPMismatchException e) {
            e.printStackTrace();
        }
        return predicts;
    }

    public int[] getPredicts(){
        return buildConnection();
    }

    public static void main(String[] args) {
        Prediction p = new Prediction();
        int[] result = p.getPredicts();
        System.out.println(result[0]+"  "+result[1]);
//        System.out.println("png(filename=\""+Constant.imagePath+"acfmax.png\")");
    }

//> D1=ts(demand1)
//> D2=ts(demand2)
//
//  画出ACF和PACF
//> ACF1= acf(D1,plot = TRUE)
//> ACF2= acf(D2,plot = TRUE)
//> PACF1= pacf(D1,plot = TRUE)
//> PACF2= pacf(D2,plot = TRUE)

//> D1.ar=arima(D1,order = c(1,0,0))
//> D2.ar=arima(D2,order = c(2,0,0))

//> f1=forecast.Arima(D1.ar,h=5)
//> f2=forecast.Arima(D2.ar,h=5)

//> acf(f1$residuals)
//> acf(f2$residuals)

//> Box.test(f1$residuals,type = "Ljung-Box")
//> Box.test(f2$residuals,type = "Ljung-Box")

}
