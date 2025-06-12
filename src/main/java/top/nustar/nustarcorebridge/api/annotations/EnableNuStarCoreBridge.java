package top.nustar.nustarcorebridge.api.annotations;

import team.idealstate.sugar.next.context.annotation.feature.RegisterProperty;
import team.idealstate.sugar.next.context.annotation.feature.Scan;
import top.nustar.nustarcorebridge.api.NuStarCoreBridgeProperties;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : NuStar
 * Date : 2025/6/12 20:39
 * Website : <a href="https://www.nustar.top">nustar's web</a>
 * Github : <a href="https://github.com/nustarworld">nustar's github</a>
 * QQ : 3318029085
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Scan("top.nustar.nustarcorebridge")
@RegisterProperty(key = NuStarCoreBridgeProperties.IS_SUB_PLUGIN, value = "true")
public @interface EnableNuStarCoreBridge {
}
