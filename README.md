Changelog
================

Relaunch64 3.2 (development)
----------------

### New features

#### code-folding
* enable or disable code-folding
* added automatic code-folding for braces { and } (can be en-/disabled)
* added automatic code-folding for labels (can be en-/disabled)
* added automatic code-folding for conditional directives (can be en-/disabled)
* added automatic code-folding for structures (can be en-/disabled)

#### editor
* automatic indention works for spaces now, too
* added commands to automatically convert spaces to tabs (and vice versa, see menu edit)
* slightly changed automatic tab/space indention. if "enter" is hit in between leading tabs/spaces, the indention is adjusted to fit the previous lines.
* shift+enter now inserts new line without automatic indention

#### user scripts
* added SOURCENAME and OUTNAME as additional placeholders for input and output file names, excluding path and extension
* added RSOURCFILE and ROUTPUT as additional placeholders for the relative paths of input and output file names.

#### GUI
* the goto-popup-list was removed and replaced by a regular list. this allows "surfing" through the goto-destinations (labels, sections, functions...) etc.
* find goto-token (label, macro, section...) by typing
* filter goto-token-list (label, macro, section...)
* "Compile and run" area was removed. Script-selection moved to log-area, assembler-syntax moved to bottom status bar (and is now part of the "source code information status bar")
* optionally show file extension in tabs
* optionally hide "Select script" field (below logs). when selecting a new script, a hidden selection box is shown and hidden again after selecting a script.

#### various
* added quick-access to compiler-help (type "ch" into text field on bottom left)

### Bug fixes
* tabs on OS X where a bit larger than planned - fixed
* fixed bug with syntax-highlighting, which, in some specific cases, did not recognize all labels
* fixed typo in help (user scripts)
* fixed bug with "insert section" command on OS X



Relaunch64 3.1.0 (current stable release)
----------------

### New features

#### completely new editor component with new features, such as:
* increased performance
* much faster syntax highlighting
* highlight current edit-line
* rectangular text selection (ctrl+mouse)
* code-folding
* proper text anti-aliasing
* improved line number display and handling
* changes on color scheme, font and tab size etc. immediately take effect

#### improved suggestion / auto-completion popup (ctrl+space and ctrl+shift+space):
* local labels outside the scope are not listed (only global labels and labels within scope)
* filter suggestion list by typing
* editor scrolls to suggestion popup when caret is not visible
* suggests all labels, constants, variables (these two go as labels, too), macros and functions.
* hierarchical namespaces for 64tass, ca65, TMPx, DreamAss (also planned for ACME and KickAss)

#### improved navigation (jump and goto functions):
* all labels, constants, variables (these two go as labels, too), macros and functions are extracted.
* hierarchical namespaces for 64tass, ca65, TMPx, DreamAss (also planned for ACME and KickAss)
* jump to next/prev code fold function

#### assembler and error handling support
* added DASM support
* added TMPx support
* syntax support for !addr pseudoop in ACME (since 0.95)
* improvements with goto error functions, error line parsing for all supported assemblers.
* highlight current error line in log
* goto error line in source when clicking on highlighted error line in log

#### GUI
* Aqua Look'n'Feel on OS X by default
* UI font scaling to better fit screen resolution (can be switched off via preferences, not supoorted with Aqua-Look'n'Feel)
* added apply-button to Other-tab of preference pane
* added close-buttons to tabs
* ctrl+c can now copy content of Relaunch64-log and assembler-log

#### various
* added alternative syntax highlighting mode (see preferences)
* many (code) improvements, code cleanup
* slight modification of Popelganda color scheme

### Bug fixes
* modified files, which should have been saved before compiling, were not checked if they really exist
* remove-script-button (preference pane) was disabled in certain cases, where it should have been enabled - fixed
* preference window could be resized, so apply-button was no longer visible. fixed, set minimum size to preference window to ensure apply-button is always visible
* relative paths, for instance when drag'n'dropping files, were not correctly extrtacted - fixed
* adding new user scripts may have messed up script-assignment to recent opened files and tabs that are re-opened on startup - fixed

Relaunch64 3.0.1 (older stable relase)
----------------

### New features
* added support for CA65
* added support for DreamAss
* added ACME macro and math function support (syntax highlighting, auto-completion)
* goto prev/next error also searches inside included files (even not opened files, which will be opened then)
* goto error now scrolls the log to the related line with error description
* auto-completion for functions, macros and scripting commands (ACME, Kick Assembler, 64tass, DreamAss) (press ctrl+shift+space while typing)
* automatic goto error line when compiling errors occur
* last 15 recently used find terms will be stored per session
* improved tab/shift+tab behaviour with selected text
* added quick references (menu view)
* added regular expression checkbox to find-command, to explicitly en-/disable search for regular expressions
* added checkboxes for whole-word and match-case search
* added syntax highlighting for scripting commands (Kick Assembler, 64tass)
* added SOURCEDIR as additional place holder in user scripts
* added "script" comment-command (see help-file)
* added new syntax highlighting color schemes
* more file extensions are now accepted on drag'n'drop
* slight modification of C64 color scheme

### Bug fixes
* bug fix with "Jump to label" function with Kick Assembler
* bug fix with "Find next" when content was changed
* bug fix with "Replace" function
* bug fix with auto-indent and unintended insert of new line
* changed auto-completion feature, which did not work with labels with less than 3 chars, and now works for labels with at least 1 char
