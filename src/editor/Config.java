package editor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;


import editor.parser.SyntaxCheckerSetter;

class Config {
	private SyntaxCheckerSetter syntaxCheckerSetter;
	private static final String confFile = Paths.get(System.getProperty("java.io.tmpdir"), "editor.conf").toString();
	private final Properties conf = new Properties();

	Config(SyntaxCheckerSetter syntaxCheckerSetter) {
		this.syntaxCheckerSetter = syntaxCheckerSetter;
		load();
		enableSyntaxChecker(conf.getProperty("syntaxChecker", ""));
	}

	private void load() {
		try {
			conf.load(new FileInputStream(confFile));
		} catch (IOException e) {
		}
	}

	void dump() {
		try {
			conf.store(new FileOutputStream(confFile), null);
		} catch (IOException e) {
		}
	}

	public void enableSyntaxChecker(final String name) {
		syntaxCheckerSetter.enable(name);
		conf.setProperty("syntaxChecker", name);
	}

	public void disableSyntaxChecker() {
		syntaxCheckerSetter.disable();
		conf.setProperty("syntaxChecker", "");
	}
}
