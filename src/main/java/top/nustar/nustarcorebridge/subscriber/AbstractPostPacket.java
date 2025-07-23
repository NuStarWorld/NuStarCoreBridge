package top.nustar.nustarcorebridge.subscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : NuStar
 * Date : 2025/7/24 02:09
 * Website : <a href="https://www.nustar.top">nustar's web</a>
 * Github : <a href="https://github.com/nustarworld">nustar's github</a>
 * QQ : 3318029085
 */
public abstract class AbstractPostPacket {

    public Map<String, String> getArgs(List<String> dataList) {
        Map<String, String> argsMap = new HashMap<>();
        dataList.remove(0);
        if (!dataList.isEmpty()) {
            for (String arg : dataList) {
                String[] split = arg.split("=", -1);
                if (split.length != 2) continue;
                argsMap.put(split[0], split[1]);
            }
        }
        return argsMap;
    }
}
