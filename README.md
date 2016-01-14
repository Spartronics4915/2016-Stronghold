2016-Stronghold
===============

![license](https://img.shields.io/github/license/Spartronics4915/2016-Stronghold.svg)
![tag](https://img.shields.io/github/tag/Spartronics4915/2016-Stronghold.svg)

Development Setup
-----------------

1. [Fork this repo](https://github.com/Spartronics4915/2016-Stronghold/fork)
1. Open Eclipse
1. Install [the FRC eclipse plugin](https://wpilib.screenstepslive.com/s/4485/m/13503/l/145002-installing-eclipse-c-java#InstallingTheDevelopmentPlugins-Option1:OnlineInstall) if you haven't already
1. `File` -> `Import...`
1. If `Git` -> `Projects from Git` is an option...
    1. Choose `Clone URI`
    1. `URI`: `https://github.com/USERNAME/2016-Stronghold.git`
    1. Entering your username and password is optional
    1. `master` branch
    1. `Import existing Eclipse projects`
1. If `Git` -> `Projects from Git` does not appear...
    1. Clone your fork
        1. Make a new folder in your home folder named "workspace"
            1. `$ mkdir ~/workspace`
        1. `$ git clone https://github.com/USERNAME/2016-Stronghold.git ~/workspace/2016-Stronghold`
        1. Choose `General` -> `Existing Projects into Workspace`
        1. `Select root directory`: `Home/workspace/2016-Stronghold`
1. `Window` -> `Preferences` -> `Java` -> `...`
    1. Clean Up
    1. Formatter
    1. Organize Imports
1. For each, click `Import` and browse to the `extra` folder. Pick the appropriate file. Make sure Sponge is selected as the style for all three.

Development Flow Post-Setup
---------------------------

1. Create a new branch for your feature: `$ git checkout -b branch-owner-username/new-branch-name`
1. Make your changes
1. Use `$ git add -- file1 file2 file3...` to "stage" your changes
1. Begin to make a commit with `$ git commit`.
1. Edit the commit message. The default editor, `vi`, is really intimidating, but simple to write a message with.
    1. Press `i` to enter "insert" mode. Any buttons you push outside of insert mode will usually be interpreted as commands, and do unexpected things.
    1. Write your message. Here's a really great guide: http://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html
    1. The summary (first line) of your commit message should follow this format:
    1. `section: short change description`, like so: `formatting: organize imports` or `drivetrain: trigger the motors`.
    1. Press `escape` to exit "insert" mode, and go to "normal" mode.
    1. Type `:wq` to save and quit, or `:q!` if you want to cancel. If you use `:q!`, the commit won't be created.
1. Rinse and repeat! When you're done making commits, move on.
1. Push your changes to your fork with `$ git push`
1. Create a pull request by visiting your fork's webpage on GitHub and clicking the green button.
1. Add a description, and be sure to mention whether you've tested the code.
1. Bug a leader to merge your request.
1. It may be rejected, or be commented on with requests to change things.
1. You can keep committing and pushing to a branch that has an active pull request -- your commits will be automatically added to the pull request.

