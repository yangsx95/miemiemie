package com.miemiemie.starter.openapi.plugin;

import com.miemiemie.core.enums.CommonEnum;
import com.miemiemie.starter.openapi.OpenApiUtil;
import org.springframework.lang.NonNull;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;

import java.util.List;
import java.util.Objects;

/**
 * 文档CommonEnum方法参数文档处理
 *
 * @author yangshunxiang
 * @since 2023/01/20
 */
public class CommonEnumParameterPlugin implements ParameterBuilderPlugin {
    @Override
    public boolean supports(@NonNull DocumentationType delimiter) {
        return true;
    }

    @Override
    public void apply(ParameterContext parameterContext) {
        // 获取api操作的所有参数
        List<ResolvedMethodParameter> parameters = parameterContext.getOperationContext().getParameters();
        parameters.stream()
                .filter(Objects::nonNull)
                .filter(p -> Enum.class.isAssignableFrom(p.getParameterType().getErasedType()))
                .filter(p -> CommonEnum.class.isAssignableFrom(p.getParameterType().getErasedType()))
                .forEach(p -> {
                    RequestParameterBuilder builder = parameterContext.requestParameterBuilder();
                    String description = OpenApiUtil.getFiledVale(builder, "description")
                            + " (" + String.join("; ", OpenApiUtil.commonEnumConstantsDescList(p.getParameterType().getErasedType())) + ")";
                    builder.description(description);
                    builder.query(sb -> sb.enumerationFacet(e -> e.allowedValues(OpenApiUtil.commonEnumConstantAvailable(p.getParameterType().getErasedType()))));
                });
    }
}
