package pojocreator;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;

import com.sun.codemodel.JCodeModel;

public class CreatePojoFromJson {

	public void convertJsonToJavaClass( File outputJavaClassDirectory, String packageName, String javaClassName,String json) 
			  throws IOException {
			    JCodeModel jcodeModel = new JCodeModel();

			    GenerationConfig config = new DefaultGenerationConfig() {
			        @Override
			        public boolean isGenerateBuilders() {
			            return true;
			        }

			        @Override
			        public SourceType getSourceType() {
			            return SourceType.JSON;
			        }
			    };

			    SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
			    mapper.generate(jcodeModel, javaClassName, packageName, json);

			    jcodeModel.build(outputJavaClassDirectory);
			}
}
