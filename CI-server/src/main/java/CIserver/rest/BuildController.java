package CIserver.rest;

import CIserver.app.Build;
import CIserver.app.SQLhandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

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
        try{
            SQLhandler sql = new SQLhandler();
            builds = sql.getHistory();
            model.addAttribute("builds", builds);
            return "all_builds";
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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
        try{
            SQLhandler sql = new SQLhandler();
            Build build = sql.getEntry(commit);
            model.addAttribute("buildInfo", build);
            return "build";
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
