package top.nustar.nustarcorebridge.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import team.idealstate.sugar.next.context.Context;
import team.idealstate.sugar.next.context.annotation.component.Configuration;
import team.idealstate.sugar.next.context.annotation.feature.DependsOn;
import team.idealstate.sugar.next.context.annotation.feature.Scope;
import top.nustar.nustarcorebridge.api.NuStarCoreBridgeProperties;

import java.util.List;

/**
 * @author NuStar<br>
 * @since 2025/8/25 12:48<br>
 */
@Configuration(uri = "config.yml", release = Context.RESOURCE_EMBEDDED + "config.yml")
@Data
@Scope(Scope.PROTOTYPE)
@DependsOn(properties = @DependsOn.Property(key = NuStarCoreBridgeProperties.IS_SUB_PLUGIN, value = "false"))
public class NuStarCoreBridgeConfiguration {
    @JsonProperty("black-placeholder")
    @JsonDeserialize(contentAs = String.class)
    private List<String> blackPlaceholderList;
}
