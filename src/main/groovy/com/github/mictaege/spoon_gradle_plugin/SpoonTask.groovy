package com.github.mictaege.spoon_gradle_plugin

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.file.FileTree
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import spoon.Launcher

import static java.io.File.pathSeparator

class SpoonTask extends DefaultTask {

    private Logger log = Logging.getLogger("spoon")

    boolean buildOnlyOutdatedFiles
    @InputFiles
    FileTree srcFolder
    @OutputDirectory
    File outFolder
    String[] processors = []
    FileCollection classpath
    int compliance

    @TaskAction
    void run() {

        List<String> params = new LinkedList<>()

        addParam(params, '--input', srcFolder.getAsPath())
        if (buildOnlyOutdatedFiles) {
            addKey(params, '--buildOnlyOutdatedFiles')
        }
        addParam(params, '--output', outFolder.getAbsolutePath())
        addParam(params, '--compliance', '' + compliance)
        if (processors.size() != 0) {
            addParam(params, '--processors', processors.join(pathSeparator))
        }
        if (!classpath.asPath.empty) {
            addParam(params, '--source-classpath', classpath.asPath)
        }
        addKey(params, '--noclasspath')
        addParam(params, '--level', "OFF")
        addKey(params, '--lines')

        def launcher = new Launcher()
        String[] args = params.toArray(new String[params.size()])
        logEnv(args)
        launcher.setArgs(args)
        launcher.run()
    }

    private logEnv(String[] args) {
        log.debug "Spoon arguments:"
        for (final String arg : args) {
            log.debug "\t$arg"
        }
    }

    private static void addParam(params, key, value) {
        addKey(params, key)
        params.add(value)
    }

    private static void addKey(params, key) {
        params.add(key)
    }
}
