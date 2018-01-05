package scripting;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class Executor {
	private final static String SCRIPT_TO_RUN = "es6" + File.separator + "out" + File.separator + "modules.consume.js"; // Transpiled
//private final static String SCRIPT_TO_RUN = "es6" + File.separator + "modules.consume.js"; // Not transpiled

	private final static String NASHORN_ARGS = "nashorn.args";
	private final static String ES_6 = "--language=es6";


	public static void main(String... args) throws Exception {

		System.out.println(String.format("Running from [%s]", System.getProperty("user.dir")));

		String script =  SCRIPT_TO_RUN;
		if (args.length > 0) {
			script = args[0];
		}

		System.setProperty(NASHORN_ARGS, ES_6);

		ScriptEngineManager factory = new ScriptEngineManager();
		List<ScriptEngineFactory> engineFactories = factory.getEngineFactories();
		System.out.println(String.format("%s factory(ies).", engineFactories.size()));
		engineFactories.stream().forEach(ef -> {
			System.out.println(String.format("%s (%s)",
					ef.getEngineName(),
					ef.getNames().stream().collect(Collectors.joining(", "))));
		});

		ScriptEngine engine = factory.getEngineByName("nashorn");
		FileReader reader = new FileReader(script);
		try {
			engine.eval(reader);
		} catch (Exception ex) {
			System.err.println("Ooops:" + ex.toString());
		} finally {
			reader.close();
		}
		engine.eval("print('Bye now.');");
	}
}
