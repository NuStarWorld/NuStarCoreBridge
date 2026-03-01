package top.nustar.nustarcorebridge.configuration;

import lombok.Data;
import team.idealstate.sugar.next.context.Context;
import team.idealstate.sugar.next.context.annotation.component.Configuration;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import team.idealstate.sugar.next.context.annotation.feature.Scope;
import top.nustar.nustarcorebridge.api.NuStarCoreBridgeProperties;

/**
 * @author NuStar<br>
 * @since 2026/3/1 23:48<br>
 */
@Configuration(uri = "debug.yml", release = Context.RESOURCE_EMBEDDED + "debug.yml")
@Data
@Scope(Scope.SINGLETON)
@DependsOn(properties = @DependsOn.Property(key = NuStarCoreBridgeProperties.IS_SUB_PLUGIN, value = "false"))
public class DebugConfiguration {

    private boolean debug;

}
