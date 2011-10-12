PgsLookAndFeel - 1.1.1
---------------------------------------------------

The PgsLookAndFeel is a modern LookAndFeel for Java/Swing. It can be used in a wide range of applications. It uses low saturated colors to drive attention of the user to the actual application and to make the user feel comfortable even when using an application for multiple hours.

Compatibility
---------------------------------------------------
I decided to rewrite parts of the UI in a way that might not be compatible with all themes. Actually only the minority of the standard themes are still working. All others look worse than before. Your own themes might not be working in PgsLookAndFeel 1.1 anymore.
This is the price I had to pay for the much improved look of the working themes. The other themes need to be improved and maybe I need to change a few internal things to support those themes as well.
All of the enhanced features of PgsLookAndFeel (like html-display-fix and wheel-scrolling tabbed panes) are now disabled by default. If you want to leave your application unchanged when the next release comes out (probably version 1.2), do not enable them. All of these features will be removed (most likely).

Working themes
----------------------------------------------------
This list is really short. I've applied the changes with focus on the SilverTheme, so this is the best-looking theme now and has become the default theme as by this release.
The ThemeFactory.GRAY theme seems to be working fine, too. Other themes are suffering from some minor visual problems. If you want to use them, you must fix them by yourself (I'd be glad if you could send me your patched versions so I can integrate them into the final/next version)

Lookahead
-----------------------------------------------------
My time is very rare at the moment so do not expect new features or versions to come out too soon. Doesn't mean this project has been abandoned, just that I must spend my time on other projects.

Unsupported components
-----------------------------------------------------
This is a section that really bugs me. If nobody contributes the missing components, these will probably be part of version 1.2.
- JSpinner
- JInternalFrame
Note: "unsupported" in this context means that it is using pure MetalLookAndFeel.

License
-----------------------------------------------------
Version 1.1 will be the last version to be released under terms of Apache Software License 2. Later versions will use the BSD-license (for compatibility with GPL).

Usage
----------------------------------------------------
To use PgsLookAndFeel in your application you have to include the following line in your startup code (before you initialize any Swing components):
	PlafOptions.setAsLookAndFeel();
If you absolutly must you can add the code at any place in your code and update the UI later on:
	PlafOptions.updateAllUIs();
But  please note that PgsLookAndFeel has not been tuned towards "hot-switching" the L&F or even the current theme. There might be problems if you switch the L&F while your application is running. Later versions will correct this issue.

If you'd like to use a different theme you'll need to call
	PlafOptions.setTheme(yourTheme);
before calling setAsLookAndFeel.
The standard theme is the SilverTheme, located at com.pagosoft.plaf.themes.SilverTheme.

Note: All of the above examples assume that you have imported the com.pagosoft.plaf.PlafOptions class.

Updating to 1.1
-----------------------------------------------------
Please remove all code that changes these settings:
- Antialiasing (will be removed on all platforms but Mustang where it is applied by default)
- HTML Display Fix (buggy, will be removed)
- Wheel Tab (buggy, will be removed)
- all "Style"-settings (will be removed)
- pgs.properties (will be removed and rewritten for 2.0)

Changes since 1.1 Beta
------------------------------------------------------
- popup menu for text components now working properly.

Changes since 1.1 Final
------------------------------------------------------
- fixed all known bugs
- support for Java 6's sortable tables
- improved text component menu code
- fixed an exception produced if a tab is added to a JTabbedPane through an action from within the pane
- adding empty icons to menus can now be disabled using an Option.

------------------------------------------------------

Thank you for trying PgsLookAndFeel 1.1.1. If you find any bugs, please let me know through the bug-tracker, mailing list or just drop me a private mail (patrick@pagosoft.com).
- Patrick Gotthardt
