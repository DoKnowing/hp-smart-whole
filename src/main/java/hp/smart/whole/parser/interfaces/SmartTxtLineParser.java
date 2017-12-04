package hp.smart.whole.parser.interfaces;

/**
 * @Author: SMA
 * @Date: 2017-09-18 10:43
 * @Explain:
 */
public abstract class SmartTxtLineParser implements SmartLineParser<String> {
    @Override
    public abstract void parser(String line);
}
