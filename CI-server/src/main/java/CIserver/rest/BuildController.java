package CIserver.rest;

import CIserver.app.Build;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for serving web content
 */
@Controller
public class BuildController {
    /**
     * Lists all builds
     * @param model model is implicitly sent through GET to server the web content
     * @return web content displaying all builds with relevant information
     */
    @GetMapping("/builds")
    public String builds(Model model) {
        List<Build> builds = new ArrayList<>();
        //Fill list with all builds
        builds.add(new Build("djk3lkj", "2024-02-05", "Build failed :("));
        builds.add(new Build("89dsfsd", "2024-02-07", "Build passed :)"));
        builds.add(new Build("vckxv8a", "2024-02-08", "Build passed :)"));
        builds.add(new Build("ufdujs8", "2024-02-11", "Build failed :("));
        model.addAttribute("builds", builds);
        return "all_builds";
    }

    /**
     * Lists build information of specific build
     * @param commit the commit SHA of the build to be displayed
     * @param model model is implicitly sent through GET to server the web content
     * @return web content displaying the build information of the commit
     */
    @GetMapping("/builds/{commit}")
    public String showCommitBuild(@PathVariable String commit, Model model) {
        // Get relevant build information for the specific commit
        Build build = new Build(commit, "2024-02-10", "Build passed :)");
        model.addAttribute("buildInfo", build);
        return "build";
    }
}
