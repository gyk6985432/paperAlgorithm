package paper.predict;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import paper.dataProcess.LoadRange;

/**
 * Created by gyk on 2016/10/14.
 */
public class Prediction {
    LoadRange loadRange = new LoadRange();
    int[] maxLoads,minLoads;


    private void getRange(){
        maxLoads = loadRange.getMaxLoads();
        minLoads = loadRange.getMinLoads();
    }

    private void buildConnection(){
        try {
            RConnection rconn = new RConnection();
            rconn.eval("tsmax = ts("+maxLoads+")");
            rconn.eval("tsmin = ts("+minLoads+")");
            REXP acfmax = rconn.eval("acf(tsmax, plot=FALSE)");
            REXP acfmin = rconn.eval("acf(tsmin, plot=FALSE)");
            REXP pacfmax = rconn.eval("pacf(tsmax, plot=FALSE)");
            REXP pacfmin = rconn.eval("pacf(tsmin, plot=FALSE)");
        } catch (RserveException e) {
            e.printStackTrace();
        }
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
