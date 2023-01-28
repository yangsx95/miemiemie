package com.miemiemie.starter.swagger.plugin;

import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.miemiemie.core.enums.CommonEnum;
import com.miemiemie.starter.swagger.util.SwaggerUtil;
import org.springframework.lang.NonNull;
import springfox.documentation.builders.PropertySpecificationBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

import java.util.List;
import java.util.Optional;

/**
 * 在文档中列举所有枚举值(<code>@ModelProperty</code>)
 *
 * @author yangshunxiang
 * @since 2023/01/20
 */
public class CommonEnumModelPropertyBuilderPlugin implements ModelPropertyBuilderPlugin {
    @Override
    public boolean supports(@NonNull DocumentationType delimiter) {
        return true;
    }

    @Override
    public void apply(ModelPropertyContext context) {
        Optional<BeanPropertyDefinition> optional = context.getBeanPropertyDefinition();
        if (!optional.isPresent()) {
            return;
        }

        addDescForEnum(context, optional.get().getField().getRawType());
    }

    private void addDescForEnum(ModelPropertyContext context, Class<?> fieldType) {
        if (!Enum.class.isAssignableFrom(fieldType)) {
            return;
        }
        if (!CommonEnum.class.isAssignableFrom(fieldType)) {
            return;
        }

        List<String> displayDescList = SwaggerUtil.commonEnumConstantsDescList(fieldType);

        PropertySpecificationBuilder builder = context.getSpecificationBuilder();
        String joinText = SwaggerUtil.getFiledVale(builder, "description")
                + " (" + String.join("; ", displayDescList) + ")";

        builder.description(joinText);
        builder.enumerationFacet(eefb -> eefb.allowedValues(SwaggerUtil.commonEnumConstantAvailable(fieldType)));
    }
}
