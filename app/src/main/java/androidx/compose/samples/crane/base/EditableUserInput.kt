/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.compose.samples.crane.base

import androidx.annotation.DrawableRes
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.samples.crane.ui.captionTextStyle
import androidx.compose.ui.graphics.SolidColor

@Composable
fun CraneEditableUserInput(
    /* BEGIN-7 - Creating a state holder */
    // This stateful composable takes some parameters such as the hint and a
    // caption which corresponds to the optional text next to the icon.
    /* BEGIN-7.1 - Why? */
    // The logic to update the textState and determine whether what's been
    // displayed corresponds to the hint or not is all in the body of the
    // CraneEditableUserInput composable. This brings some downsides with it:
    // . The value of the TextField is not hoisted and therefore cannot be
    // controlled from outside, making testing harder.
    // . The logic of this composable could become more complex and the internal
    // state could be out of sync more easily.
    /* BEGIN-7.5 - Using the state holder */
    // You're going to use EditableUserInputState instead of text and isHint,
    // but you don't want to just use it as an internal state in
    // CraneEditableUserInput as there's no way for the caller composable to
    // control the state. Instead, you want to hoist EditableUserInputState so
    // that callers can control the state of CraneEditableUserInput. If you
    // hoist the state, then the composable can be used in previews and be
    // tested more easily since you're able to modify its state from the caller.
    // To do this, you need to change the parameters of the composable function
    // and give it a default value in case it is needed. Because you might want
    // to allow CraneEditableUserInput with empty hints, add a default argument.
    // hint: String,
    state: EditableUserInputState = rememberEditableUserInputState(""),
    /* END-7.5 */
    /* END-7.1 */
    /* END-7 */
    caption: String? = null,
    @DrawableRes vectorImageId: Int? = null
    /* BEGIN-7.5 - Using the state holder */
    // The onInputChanged parameter is not there anymore! Since the state can be
    // hoisted, if callers want to know if the input changed, they can control
    // the state and pass that state into this function.
    //, onInputChanged: (String) -> Unit
    /* END-7.5 */
) {
    /* BEGIN-7.5 - Using the state holder */
    // Use the hoisted state instead of the internal state that was used before.
//    var textState by remember { mutableStateOf(hint) }
//    val isHint = { textState == hint }
//    CraneBaseUserInput(
//        caption = caption,
//        tintIcon = { !isHint() },
//        showCaption = { !isHint() },
//        vectorImageId = vectorImageId
//    ) {
//        BasicTextField(
//            value = textState,
//            onValueChange = {
//                textState = it
//                if (!isHint()) onInputChanged(textState)
//            },
//            textStyle = if (isHint()) {
//                captionTextStyle.copy(color = LocalContentColor.current)
//            } else {
//                MaterialTheme.typography.body1.copy(
//                    color = LocalContentColor.current)
//            },
//            cursorBrush = SolidColor(LocalContentColor.current)
//        )
//    }
    CraneBaseUserInput(
        caption = caption,
        tintIcon = { !state.isHint },
        showCaption = { !state.isHint },
        vectorImageId = vectorImageId
    ) {
        BasicTextField(
            value = state.text,
            onValueChange = { state.updateText(it) },
            textStyle = if (state.isHint) {
                captionTextStyle.copy(color = LocalContentColor.current)
            } else {
                MaterialTheme.typography.body1.copy(
                    color = LocalContentColor.current)
            },
            cursorBrush = SolidColor(LocalContentColor.current)
        )
    }
    /* END-7.5 */
}

/* BEGIN-7.1 - Why? */
// By creating a state holder responsible for the internal state of this
// composable, you can centralize all state changes in one place. With this,
// it's more difficult for the state to be out of sync, and the related logic is
// all grouped together in a single class. Furthermore, this state can be easily
// hoisted up and can be consumed from callers of this composable.
// In this case, hoisting the state is a good idea since this is a low-level UI
// component that might be reused in other parts of the app. Therefore, the more
// flexible and controllable it is, the better.
/* END-7.1 */
/* BEGIN-7.2 - Creating the state holder */
// This class should have the following traits:
// . text is a mutable state of type String, just as you have in
// CraneEditableUserInput. It's important to use mutableStateOf so that Compose
// tracks changes to the value and recomposes when changes happen.
// . text is a var, with a private set so it can't be directly mutated from
// outside the class. Instead of making this variable public, you can expose an
// event updateText to modify it, which makes the class the single source of
// truth.
// . The class takes an initialText as a dependency that is used to initialize
// text.
// . The logic to know if the text is the hint or not is in the isHint property
// that performs the check on-demand.
// If the logic gets more complex in the future, you only need to make changes
// to one class: EditableUserInputState.
class EditableUserInputState(private val hint: String, initialText: String) {

    var text by mutableStateOf(initialText)
    private set

    fun updateText(newText: String) {
        text = newText
    }

    val isHint: Boolean
    get() = text == hint

    /* BEGIN-7.4 */
    // A Saver describes how an object can be converted into something which is
    // Saveable. Implementations of a Saver need to override two functions:
    // . save to convert the original value to a saveable one.
    // . restore to convert the restored value to an instance of the original
    // class.
    // For this case, instead of creating a custom implementation of Saver for
    // the EditableUserInputState class, you can use some of the existing
    // Compose APIs such as listSaver or mapSaver (that stores the values to
    // save in a List or Map) to reduce the amount of code that you need to
    // write.
    // It's a good practice to place Saver definitions close to the class they
    // work with. Because it needs to be statically accessed, add the Saver for
    // EditableUserInputState in a companion object.
    companion object {
        val Saver: Saver<EditableUserInputState, *> = listSaver(
            save = { listOf(it.hint, it.text) },
            restore = {
                EditableUserInputState(
                    hint = it[0],
                    initialText = it[1],
                )
            }
        )
    }
    /* END-7.4 */
}
/* END-7.2 */

/* BEGIN-7.3 - Remembering the state holder */
// State holders always need to be remembered in order to keep them in the
// Composition and not create a new one every time. It's a good practice to
// create a method in the same file that does this to remove boilerplate and
// avoid any mistakes that might occur.
// If you only remember this state, it won't survive activity recreations. To
// achieve that, you can use the rememberSaveable API instead which behaves
// similarly to remember, but the stored value also survives activity and
// process recreation. Internally, it uses the saved instance state mechanism.
// rememberSaveable does all this with no extra work for objects that can be
// stored inside a Bundle. That's not the case for the EditableUserInputState
// class that you created in your project. Therefore, you need to tell
// rememberSaveable how to save and restore an instance of this class using a
// Saver.
@Composable
fun rememberEditableUserInputState(hint: String): EditableUserInputState =
/* BEGIN-7.4 - Creating a custom saver */
//    remember(hint) {
//        EditableUserInputState(hint, hint)
//    }
    rememberSaveable(hint, saver = EditableUserInputState.Saver) {
        EditableUserInputState(hint, hint)
    }
/* END-7.4 */
/* END-7.3 */
