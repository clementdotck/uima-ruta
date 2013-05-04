/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.uima.ruta.ide.ui.wizards;

import org.eclipse.dltk.ui.wizards.ProjectWizardFirstPage;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

final class RutaProjectWizardFirstPage extends ProjectWizardFirstPage {

  private Button useAnalysis;

  private Group packagesGroup;

  private Label labelElement;

  public RutaProjectWizardFirstPage() {
    setTitle(RutaWizardMessages.ProjectCreationWizardFirstPage_title);
    setDescription(RutaWizardMessages.ProjectCreationWizardFirstPage_description);
  }

  
  @Override
  protected IInterpreterGroup createInterpreterGroup(Composite parent) {
    return new DefaultInterpreterGroup(parent);
  }

  @Override
  protected boolean interpeterRequired() {
    /* Specially allow to create TCL project without interpreter */
    return false;
  }

  // protected void createCustomGroups(Composite composite) {
  // super.createCustomGroups(composite);
  //
  // packagesGroup = new Group(composite, SWT.NONE);
  // GridData gridData = new GridData(GridData.FILL, SWT.FILL, true, false);
  // gridData.widthHint = convertWidthInCharsToPixels(50);
  // packagesGroup.setLayoutData(gridData);
  // packagesGroup.setLayout(new GridLayout(1, false));
  // packagesGroup
  // .setText(RutaWizardMessages.RutaProjectWizardFirstPage_packageDetector);
  // this.useAnalysis = new Button(packagesGroup, SWT.CHECK);
  // this.useAnalysis
  // .setText(RutaWizardMessages.RutaProjectWizardFirstPage_packageDetector_checkbox);
  // this.useAnalysis.setSelection(true);
  // labelElement = new Label(packagesGroup, SWT.NONE);
  // labelElement
  // .setText(RutaWizardMessages.RutaProjectWizardFirstPage_packageDetector_description);
  // Observer o = new Observer() {
  // public void update(Observable o, Object arg) {
  // boolean inWorkspace = fLocationGroup.isInWorkspace();
  // packagesGroup.setEnabled(!inWorkspace);
  // useAnalysis.setEnabled(!inWorkspace);
  // labelElement.setEnabled(!inWorkspace);
  // }
  // };
  // fLocationGroup.addObserver(o);
  // ((RutaInterpreterGroup) getInterpreterGroup()).addObserver(o);
  // }

  public boolean useAnalysis() {
    final boolean result[] = { false };
    useAnalysis.getDisplay().syncExec(new Runnable() {
      public void run() {
        result[0] = useAnalysis.getSelection();
      }
    });
    return result[0];
  }

}